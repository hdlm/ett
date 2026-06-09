package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import kotlinx.coroutines.flow.Flow

class ActivityElapsedTimeYesterdayInfoUseCase(
    private val localRepository: TimerTrackingLocalRepository
) {
    operator fun invoke(): Flow<List<ActivityTotalTimeQuery>> {
        val yesterdayPeriod = TimeUtils.getYesterdayPeriod()

        return localRepository.observeActivityTotalTimeQuery(
            startDate = yesterdayPeriod.first,
            endDate = yesterdayPeriod.second
        )
    }

}