package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ActivityElapsedTimeWeeklyInfoUseCase : KoinComponent {
    val localRepository: TimerTrackingLocalRepository
        get() = get()

    operator fun invoke(): Flow<List<ActivityTotalTimeQuery>> {
        val weekPeriod = TimeUtils.getWeekPeriod()

        return localRepository.observeActivityTotalTimeQuery(
            startDate = weekPeriod.first,
            endDate = weekPeriod.second
        )
    }

}