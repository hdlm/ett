/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.data.database.entities.relations.TimerTrackingQuery
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TimerTrackingInfoUseCase : KoinComponent {
    val localRepository: TimerTrackingLocalRepository
        get() = get()

    operator fun invoke(): Flow<List<TimerTrackingQuery>> =
        localRepository.observeAllTimersTrackingQuery()

}