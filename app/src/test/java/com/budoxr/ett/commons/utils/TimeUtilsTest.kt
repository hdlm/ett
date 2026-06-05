/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.commons.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeUtilsTest {

    @Test
    fun `calculateTimestampDifference should return correct difference in seconds`() {
        val start = "2026-02-21 15:00:00"
        val end = "2026-02-21 15:01:30"
        val expected = 90L
        val actual = TimeUtils.calculateTimestampDifference(start, end)
        assertEquals(expected, actual)
    }

    @Test
    fun `calculateTimestampDifference should return negative if end is before start`() {
        val start = "2026-02-21 15:01:00"
        val end = "2026-02-21 15:00:00"
        val expected = -60L
        val actual = TimeUtils.calculateTimestampDifference(start, end)
        assertEquals(expected, actual)
    }

    @Test
    fun `calculateTimestampDifference should return 0 if format is invalid`() {
        val start = "invalid"
        val end = "2026-02-21 15:00:00"
        val expected = 0L
        val actual = TimeUtils.calculateTimestampDifference(start, end)
        assertEquals(expected, actual)
    }

    @Test
    fun `calculateTimestampDifference should handle different days correctly`() {
        val start = "2026-02-21 23:59:00"
        val end = "2026-02-22 00:01:00"
        val expected = 120L
        val actual = TimeUtils.calculateTimestampDifference(start, end)
        assertEquals(expected, actual)
    }
}
