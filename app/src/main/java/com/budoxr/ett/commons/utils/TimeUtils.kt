/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.commons.utils

import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.DayOfWeek
import java.time.LocalDate


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

    /**
     * Calculates the dates corresponding to last Sunday and Saturday based on the current date.
     * Returns a [Pair] of strings formatted in ISO-8601 layout (YYYY-MM-DD).
     *
     * @param today The reference date to calculate from. Defaults to [LocalDate.now].
     */
    fun getWeekPeriod(today: LocalDate = LocalDate.now()): Pair<String, String> {
        // 1. Get the current day of the week (e.g., FRIDAY)
        val currentDayOfWeek = today.dayOfWeek

        // 2. Calculate last Sunday
        // In java.time, DayOfWeek.SUNDAY value is 7.
        // We determine the offset to step backward to the most recent Sunday.
        val daysSinceSunday = if (currentDayOfWeek == DayOfWeek.SUNDAY) 0 else currentDayOfWeek.value
        val sundayDate = today.minusDays(daysSinceSunday.toLong())

        // 3. Calculate the upcoming Saturday
        // DayOfWeek.SATURDAY value is 6.
        // We compute the remaining days to reach Saturday.
        val daysUntilSaturday = DayOfWeek.SATURDAY.value - currentDayOfWeek.value
        val saturdayDate = if (currentDayOfWeek == DayOfWeek.SUNDAY) {
            today.plusDays(DayOfWeek.SATURDAY.value.toLong()) // If today is Sunday, Saturday will be in 6 days
        } else {
            today.plusDays(daysUntilSaturday.toLong())
        }

        // 4. Format dates to "YYYY-MM-DD"
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        return Pair(sundayDate.format(formatter), saturdayDate.format(formatter))
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


