package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class ActivityElapsedTimeMonthlyInfoUseCase(
    private val localRepository: TimerTrackingLocalRepository
): KoinComponent {

    operator fun invoke(): Flow<List<ActivityTotalTimeQuery>> {
        val monthlyPeriod = TimeUtils.getMonthlyPeriod()
        return localRepository.observeActivityTotalTimeQuery(
            startDate = monthlyPeriod.first,
            endDate = monthlyPeriod.second
        )
    }

}