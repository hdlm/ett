package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class ActivityElapsedTimeDailyInfoUseCase(
    private val localRepository: TimerTrackingLocalRepository
) : KoinComponent {

    operator fun invoke(): Flow<List<ActivityTotalTimeQuery>> {
        val dailyPeriod = TimeUtils.getDailyPeriod()
        return localRepository.observeActivityTotalTimeQuery(
            startDate = dailyPeriod.first,
            endDate = dailyPeriod.second
        )
    }

}