package com.budoxr.ett.commons.utils

import java.time.LocalDateTime
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
}

fun <T> T.toTimestamp(): String {
    return LocalDateTime.now().format(TimeUtils.timestampFormatter)
}

