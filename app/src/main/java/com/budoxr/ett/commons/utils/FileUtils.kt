package com.budoxr.ett.commons.utils

import android.content.Context
import android.os.Environment
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
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

    companion object {
        private const val TAG = "che.FileUtils"
    }
}