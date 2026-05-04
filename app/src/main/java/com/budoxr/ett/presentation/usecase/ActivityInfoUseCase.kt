package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.repositories.ActivityLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.util.Collections.addAll

class ActivityInfoUseCase : KoinComponent {
    val localRepository: ActivityLocalRepository
        get() = get()

    operator fun invoke(): Flow<Set<ActivityEntity>> = localRepository.observeAll()
        .map { list ->
            list.toSortedSet( compareBy<ActivityEntity> { it.name } )

        }

    operator fun invoke(activityId: Int) = localRepository.observeById(activityId)

}