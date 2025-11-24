package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.data.database.entities.relations.TimersWithActivity
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TimerTrackingActiveInfoUseCase : KoinComponent {
    val localRepository: TimerTrackingLocalRepository
        get() = get()

    operator fun invoke(): Flow<List<TimersWithActivity>> {
        return localRepository.observeAllActiveTimers()
    }
}