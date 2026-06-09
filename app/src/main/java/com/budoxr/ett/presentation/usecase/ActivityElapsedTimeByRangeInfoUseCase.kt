package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.commons.utils.TimeUtils
import com.budoxr.ett.data.database.entities.relations.ActivityTotalTimeQuery
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import timber.log.Timber

class ActivityElapsedTimeByRangeInfoUseCase(
    private val localRepository: TimerTrackingLocalRepository
) : KoinComponent {

    operator fun invoke(dateRange: Pair<String,String>?): Flow<List<ActivityTotalTimeQuery>> {
        val period = dateRange ?: TimeUtils.getWeekPeriod()
        Timber.tag(TAG).d("called() -> period: ${period.first} - ${period.second}")


        return localRepository.observeActivityTotalTimeQuery(
            startDate = period.first,
            endDate = period.second
        )
    }

    companion object {
        private const val TAG = "che.ActivityElapsedTimeByRangeInfoUseCase"
    }
}