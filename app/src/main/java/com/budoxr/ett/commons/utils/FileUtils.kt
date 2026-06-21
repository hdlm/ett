/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.commons.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

enum class CsvEncoding(val charset: Charset) {
    UTF_8(StandardCharsets.UTF_8),
    UTF_16LE(StandardCharsets.UTF_16LE),
    UTF_16BE(StandardCharsets.UTF_16BE),
    ISO_8859_1(StandardCharsets.ISO_8859_1),
    UNKNOWN(StandardCharsets.UTF_8) // Fallback safely
}


class FileUtils(private val context: Context) : KoinComponent {
    /**
     * The method is responsible to detect the encoding of the specific file
     * @param inputStream  file to be analyzed
     */
    fun detectEncoding(inputStream: InputStream): CsvEncoding {
        val bom = ByteArray(4)
        val bytesRead = inputStream.use { stream ->
            stream.read(bom, 0, bom.size)
        }

        if (bytesRead < 2) return CsvEncoding.UNKNOWN

        // Analyze Byte Order Mark (BOM)
        return when {
            bom[0] == 0xEF.toByte() && bom[1] == 0xBB.toByte() && bom[2] == 0xBF.toByte() -> CsvEncoding.UTF_8
            bom[0] == 0xFF.toByte() && bom[1] == 0xFE.toByte() -> CsvEncoding.UTF_16LE
            bom[0] == 0xFE.toByte() && bom[1] == 0xFF.toByte() -> CsvEncoding.UTF_16BE
            else -> {
                // Heuristic or fallback: Default to standard UTF-8 or check for Latin-1 characteristics
                CsvEncoding.UTF_8
            }
        }
    }

    /**
     * Returns the reference to the app-specific Download folder in the internal local storage.
     * Path example: /storage/emulated/0/Android/data/com.budoxr.ett/files/Download
     */
    fun getDownloadFolder(): File? {
        val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        Timber.tag(TAG).d("getDownloadFolder() -> Returning: ${folder?.absolutePath}")
        return folder
    }

    /**
     * The method returns the content of the CSV file in a String
     * @param csvFile File to CSV
     * @return String with all the content of the file
     */
    @Throws(FileNotFoundException::class, IOException::class)
    fun readCsvContent(csvFile: File): String {
        return try {
            val encoding: CsvEncoding = detectEncoding(csvFile.inputStream())
            Timber.tag(TAG).i("Csv file encoding detected: ${encoding.charset.name()}")

            csvFile
                .inputStream()
                .bufferedReader(Charset.forName(encoding.charset.name()))
                .use(BufferedReader::readText)
        } catch (e: IOException) {
            throw IOException("Error reading the file: '${csvFile.name}' from the cache's directory: ${e.message}", e)
        }
    }

    /**
     * The method returns the content of the CSV file in a String
     * @param jsonFile File to JSON
     * @return String with all the content of the file
     */
    @Throws(FileNotFoundException::class, IOException::class)
    fun readJsonContent(jsonFile: File): String {
        return try {
            val encoding: CsvEncoding = detectEncoding(jsonFile.inputStream())
            Timber.tag(TAG).i("Json file encoding detected: ${encoding.charset.name()}")

            jsonFile
                .inputStream()
                .bufferedReader(Charset.forName(encoding.charset.name()))
                .use(BufferedReader::readText)
        } catch (e: IOException) {
            throw IOException("Error reading the file: '${jsonFile.name}' from the cache's directory: ${e.message}", e)
        }
    }

    /**
     * Saves a JSON string directly into the public 'Download' folder using the MediaStore API.
     * @param fileName The desired name of the file (e.g., "activities_backup.json")
     * @param jsonString The serialized JSON payload.
     */
    @Throws(FileNotFoundException::class, IOException::class)
    suspend fun saveJsonToPublicDownloads(fileName: String, jsonString: String): Unit {
        return withContext(Dispatchers.IO) {
            runCatching {
                val resolver = context.contentResolver

                // Define metadata details for the file collection record
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/json")

                    // On Android 10 (API 29) and above, explicitly target the standard Download folder path
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    }
                }

                // Query the external volume storage directory
                val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI
                } else {
                    // Fallback configuration for legacy API levels
                    MediaStore.Files.getContentUri("external")
                }

                // Insert record allocation slot into system index
                val fileUri = resolver.insert(collectionUri, contentValues)
                    ?: throw IOException("Failed to create MediaStore entry in Downloads.")

                // Stream our byte contents straight to the allocated location uri
                resolver.openOutputStream(fileUri)?.use { outputStream ->
                    outputStream.write(jsonString.toByteArray(Charsets.UTF_8))
                } ?: throw IOException("Failed to open output stream for URI: $fileUri")
            }
        }
    }

    /**
     * Reads a JSON backup file from a given Content Uri and returns the raw JSON string.
     */
    suspend fun readJsonContent(uri: Uri): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val resolver = context.contentResolver

                // Fix: Using openFileDescriptor as a workaround for some devices (like Samsung)
                // where openInputStream might fail with SecurityException even if URI permissions were granted.
                val pfd = resolver.openFileDescriptor(uri, "r")
                    ?: throw IllegalStateException("Could not resolve open stream access for: $uri")

                val jsonString = pfd.use { descriptor ->
                    FileInputStream(descriptor.fileDescriptor).use { inputStream ->
                        BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8)).use { reader ->
                            reader.readText()
                        }
                    }
                }

                Result.success(jsonString)
            } catch (e: Exception) {
                Timber.tag(TAG).e(e, "importActivitiesFromUri() -> Error during import for URI: $uri")
                Result.failure(e)
            }
        }
    }

    /**
     * Copies a file from a given Uri to the app's internal cache directory.
     * This is useful to avoid SecurityException when accessing URIs from SAF later or in background threads.
     * @param uri The source Uri
     * @param fileName The name of the file in cache
     * @return The File in cache, or null if it fails
     */
    suspend fun copyUriToCache(uri: Uri, fileName: String): File? {
        Timber.tag(TAG).d("copyUriToCache() -> URI: $uri, fileName: $fileName")
        return withContext(Dispatchers.IO) {
            try {
                val cacheFile = File(context.cacheDir, fileName)
                val resolver = context.contentResolver

                // Fix: Using openFileDescriptor as a workaround for some devices (like Samsung)
                resolver.openFileDescriptor(uri, "r")?.use { pfd ->
                    FileInputStream(pfd.fileDescriptor).use { inputStream ->
                        cacheFile.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                } ?: return@withContext null

                cacheFile
            } catch (e: Exception) {
                Timber.tag(TAG).e(e, "copyUriToCache() -> Error copying URI: $uri to cache")
                null
            }
        }
    }

    companion object {
        private const val TAG = "che.FileUtils"
    }
}
