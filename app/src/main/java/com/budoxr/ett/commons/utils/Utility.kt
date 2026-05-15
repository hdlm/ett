package com.budoxr.ett.commons.utils

object Utility {
    /**
     * Formats a long value to show only the last three digits,
     * padded with leading zeros if the value is less than 100.
     */
    fun formatLastThreeDigits(value: Long): String {
        // Get the last 3 digits using modulo 1000
        val lastThree = value % 1000
        // Format with leading zeros, minimum 3 characters
        return "%03d".format(lastThree)
    }
}