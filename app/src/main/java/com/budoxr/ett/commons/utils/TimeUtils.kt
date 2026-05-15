package com.budoxr.ett.commons.utils

import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * The object permits to get the current time system and return a String
 * in a TimeStamp format: 2026-02-21 15:45:10
 */
object TimeUtils {
    val timestampFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    /**
     * The method calculate the difference in seconds between two dates.
     * @param startTimestamp String with the start timestamp value.
     * @param endTimestamp String with the end timestamp value.
     * @return the difference in seconds between the two dates or 0 in case of error.
     */
    fun calculateTimestampDifference(startTimestamp: String, endTimestamp: String): Long {
        return try {
            val dateTimeIni = LocalDateTime.parse(startTimestamp, timestampFormatter)
            val dateTimeFin = LocalDateTime.parse(endTimestamp, timestampFormatter)

            ChronoUnit.SECONDS.between(dateTimeIni, dateTimeFin)
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Converts seconds into a formatted string (HH:MM:SS or MM:SS).
     */
    fun Long.toTimestampFormat(): String {
        val hours = this / 3600
        val minutes = (this % 3600) / 60
        val seconds = this % 60

        return if (hours > 0) {
            // Format as 01:30:15
            "%02d:%02d:%02d".format(hours, minutes, seconds)
        } else {
            // Format as 03:00
            "%02d:%02d".format(minutes, seconds)
        }
    }

    /**
     * Formats the difference between now and the [startTimestamp]
     * into a HH:mm:ss string.
     */
    fun formatElapsedTime(startTimestamp: Long): String {
        val elapsedMillis = System.currentTimeMillis() - startTimestamp
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / (1000 * 60)) % 60
        val hours = (elapsedMillis / (1000 * 60 * 60))

        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }
}

fun <T> T.toTimestamp(): String {
    return LocalDateTime.now().format(TimeUtils.timestampFormatter)
}


/**
 * Converts a nullable string formatted as "yyyy-MM-dd HH:mm:ss" to Epoch Millis.
 * Extends String? to handle nullability gracefully.
 */
fun String?.toEpochMillis(formatter: DateTimeFormatter): Long {
    // 1. Guard clause for null or empty strings
    if (this.isNullOrBlank()) return 0L

    return try {
        // 2. Use 'this' to refer to the string value
        val localDateTime = LocalDateTime.parse(this, formatter)

        // 3. Convert to absolute time using the system's default timezone
        localDateTime.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    } catch (e: Exception) {
        // 4. Log the error (optional, using Timber as seen in your files)
        Timber.e(e, "Failed to parse timestamp: $this")
        0L
    }
}


