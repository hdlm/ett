/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.data.database.entities.relations.ActivityWithTimers
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import org.koin.core.component.KoinComponent

class ActivitiesWithTimersUseCase(
    private val localRepository: TimerTrackingLocalRepository,
): KoinComponent {

    suspend operator fun invoke(): List<ActivityWithTimers> =
        localRepository.getAllActivitiesWithTimers()

}