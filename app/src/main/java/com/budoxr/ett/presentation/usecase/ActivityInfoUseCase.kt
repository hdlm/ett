package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.data.database.repositories.ActivityLocalRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ActivityInfoUseCase : KoinComponent {
    val localRepository: ActivityLocalRepository
        get() = get()

    operator fun invoke() = localRepository.observeAll()
    operator fun invoke(activityId: Int) = localRepository.observeById(activityId)

}