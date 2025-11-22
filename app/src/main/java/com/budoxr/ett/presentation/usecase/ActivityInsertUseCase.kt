package com.budoxr.ett.presentation.usecase

import com.budoxr.ett.data.database.entities.ActivityEntity
import com.budoxr.ett.data.database.repositories.ActivityLocalRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ActivityInsertUseCase : KoinComponent {
    private val repository: ActivityLocalRepository by inject()

    suspend operator fun invoke(activity: ActivityEntity) : Unit =
        repository.insert(activity)

}