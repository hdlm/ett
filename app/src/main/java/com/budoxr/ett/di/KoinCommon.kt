package com.budoxr.ett.di

import android.content.Context
import androidx.room.Room
import com.budoxr.ett.data.database.AppDatabase
import com.budoxr.ett.data.database.repositories.ActivityLocalRepository
import com.budoxr.ett.data.database.repositories.ActivityLocalRepositoryImpl
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepositoryImpl
import com.budoxr.ett.presentation.presenters.ActivityFormViewModel
import com.budoxr.ett.presentation.presenters.ActivityViewModel
import com.budoxr.ett.presentation.presenters.MonitorViewModel
import com.budoxr.ett.presentation.usecase.ActivityInsertUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingActiveInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object Modules {
    val appModule = module {
        viewModel { ActivityFormViewModel() }
        viewModel { MonitorViewModel() }
        viewModel { ActivityViewModel() }
    }

    fun provideDataBase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "ett.db"
        ).fallbackToDestructiveMigration(false).build()

    fun provideActivityDao(db: AppDatabase) = db.activityDao()
    fun provideTimeTrackingDao(db: AppDatabase) = db.timeTrackingDao()

    val databaseModule = module {
        single { provideDataBase(androidContext()) }
        single { provideActivityDao(get()) }
        single { provideTimeTrackingDao(get()) }

        factory<ActivityLocalRepository> { ActivityLocalRepositoryImpl() }
        factory { ActivityInsertUseCase() }
        factory<TimerTrackingLocalRepository> { TimerTrackingLocalRepositoryImpl() }
        factory { TimerTrackingActiveInfoUseCase() }
        factory { TimerTrackingInsertUseCase() }

    }

}


