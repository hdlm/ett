/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.commons.utils

import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityInsertUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CsvHelperTest {

    private lateinit var csvHelper: CsvHelper
    private val activityInfoUseCase: ActivityInfoUseCase = mockk()
    private val activityInsertUseCase: ActivityInsertUseCase = mockk()
    private val timerTrackingUseCase: TimerTrackingInsertUseCase = mockk()

    @Before
    fun setUp() {
        csvHelper = CsvHelper()
    }

    @Test
    fun `processCsvRawData should parse and insert records correctly`() = runTest {
        // Arrange
        val rawData = """
            A;B;C;D;E;F;G;H;Fecha B3;Fecha B4
            Sleep;352;;357;287;360;;;2026-05-17;2026-05-23
            TikTok;103;134;142;;;;;2026-05-17;2026-05-23
        """.trimIndent()

        // Mock existing activities (empty set)
        coEvery { activityInfoUseCase.invoke(true) } returns emptySet()

        // Mock activity insertion
        coEvery { activityInsertUseCase.invoke(any()) } returnsMany listOf(1L, 2L)

        // Mock timer tracking insertion
        coEvery { timerTrackingUseCase.invoke(any()) } returns 1L

        // Act
        csvHelper.processCsvRawData(
            rawData = rawData,
            activities = activityInfoUseCase.invoke(true),
            activityInsertUseCase = activityInsertUseCase,
            timerTrackingUseCase = timerTrackingUseCase
        )

        // Assert
        // Verify Sleep activity was inserted
        coVerify { activityInsertUseCase.invoke(match { it.name == "Sleep" }) }
        // Verify TikTok activity was inserted
        coVerify { activityInsertUseCase.invoke(match { it.name == "TikTok" }) }

        // Verify TimerTracking records for Sleep: 4 days have values (352, 357, 287, 360)
        coVerify(exactly = 4) {
            timerTrackingUseCase.invoke(match { it.activityId == 1L })
        }

        // Verify TimerTracking records for TikTok: 3 days have values (103, 134, 142)
        coVerify(exactly = 3) {
            timerTrackingUseCase.invoke(match { it.activityId == 2L })
        }
    }

    @Test
    fun `processCsvRawData should skip records with invalid field count`() = runTest {
        // Arrange
        val rawData = "Activity;100;2026-05-17;2026-05-23" // Missing fields

        coEvery { activityInfoUseCase.invoke(true) } returns emptySet()

        // Act
        csvHelper.processCsvRawData(
            rawData = rawData,
            activities = activityInfoUseCase.invoke(true),
            activityInsertUseCase = activityInsertUseCase,
            timerTrackingUseCase = timerTrackingUseCase
        )

        // Assert
        coVerify(exactly = 0) { activityInsertUseCase.invoke(any()) }
        coVerify(exactly = 0) { timerTrackingUseCase.invoke(any()) }
    }
}
