/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.budoxr.ett.commons.utils.CsvHelper
import com.budoxr.ett.commons.utils.FileUtils
import com.budoxr.ett.commons.utils.Utility
import com.budoxr.ett.data.database.AppDatabase
import com.budoxr.ett.data.database.DatabaseBackupManager
import com.budoxr.ett.data.database.DatabaseBackupManagerImpl
import com.budoxr.ett.data.database.repositories.ActivityLocalRepository
import com.budoxr.ett.data.database.repositories.ActivityLocalRepositoryImpl
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepository
import com.budoxr.ett.data.database.repositories.TimerTrackingLocalRepositoryImpl
import com.budoxr.ett.data.datastore.repositories.UserPreferencesRepository
import com.budoxr.ett.presentation.presenters.ActivityFormViewModel
import com.budoxr.ett.presentation.presenters.ActivityViewModel
import com.budoxr.ett.presentation.presenters.ManageBackupViewModel
import com.budoxr.ett.presentation.presenters.MonitorViewModel
import com.budoxr.ett.presentation.presenters.ProgressChartViewModel
import com.budoxr.ett.presentation.usecase.ActivitiesWithTimersUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeByRangeInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeDailyInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeMonthlyInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeWeeklyInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityElapsedTimeYesterdayInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityInfoUseCase
import com.budoxr.ett.presentation.usecase.ActivityInsertUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingDeleteUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingVisibleInfoUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingInsertUseCase
import com.budoxr.ett.presentation.usecase.TimerTrackingWeeklyInfoUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.squareup.moshi.Moshi

object Modules {
    val appModule = module {
        viewModel { (activityId: Long) -> ActivityFormViewModel(activityId, get(), get()) }
        viewModel { MonitorViewModel(get(), get(), get(), get(), get(), get(), get()) }
        viewModel { ActivityViewModel(get(), get() ) }
        viewModel { ProgressChartViewModel(get(), get(), get(), get(), get(), get()) }
        viewModel { (typeOperation: Int) -> ManageBackupViewModel(typeOperation, get(), get(), get(), get(), get(), get(), get(), get()) }

        factory { FileUtils(androidContext()) }
        factory { CsvHelper() }
        factory { Utility(androidContext()) }
        single<Moshi> { 
            Moshi.Builder()
                .build() 
        }
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
        single { UserPreferencesRepository(androidContext()) }
        
        // Provide AppDatabase singleton
        single { provideDataBase(androidContext()) }
        
        // Explicitly provide RoomDatabase type by delegating to the AppDatabase singleton
        single<RoomDatabase> { get<AppDatabase>() }
        
        single { provideActivityDao(get()) }
        single { provideTimeTrackingDao(get()) }

        factory<ActivityLocalRepository> { ActivityLocalRepositoryImpl() }
        factory { ActivityInsertUseCase() }
        factory { ActivityInfoUseCase() }
        factory { ActivitiesWithTimersUseCase(get()) }
        factory { ActivityElapsedTimeDailyInfoUseCase(get()) }
        factory { ActivityElapsedTimeYesterdayInfoUseCase(get()) }
        factory { ActivityElapsedTimeWeeklyInfoUseCase(get()) }
        factory { ActivityElapsedTimeMonthlyInfoUseCase(get()) }
        factory { ActivityElapsedTimeByRangeInfoUseCase(get()) }

        factory<TimerTrackingLocalRepository> { TimerTrackingLocalRepositoryImpl() }
        
        factory<DatabaseBackupManager> { 
            DatabaseBackupManagerImpl(
                context = androidContext(), 
                roomDatabase = get(), 
                databaseName = "ett.db",
                fileUtils = get(),
                moshi = get()
            )
        }
        
        factory { TimerTrackingVisibleInfoUseCase() }
        factory { TimerTrackingInsertUseCase() }
        factory { TimerTrackingWeeklyInfoUseCase() }
        factory { TimerTrackingInfoUseCase() }
        factory { TimerTrackingDeleteUseCase() }
    }
}
