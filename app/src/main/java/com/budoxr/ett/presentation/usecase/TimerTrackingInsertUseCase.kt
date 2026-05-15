/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.data.database.entities.TimerTrackingEntity
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TimerTrackingInsertUseCase : KoinComponent {
    private val repository: TimerTrackingLocalRepository
        get() = get()

    suspend operator fun invoke(timerTracking: TimerTrackingEntity) : Long =
        repository.insert(timerTracking)

}