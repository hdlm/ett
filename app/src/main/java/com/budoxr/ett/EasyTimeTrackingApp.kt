/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett

import android.app.Application
import com.budoxr.ett.di.Modules.appModule
import com.budoxr.ett.di.Modules.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.error.KoinApplicationAlreadyStartedException
import org.koin.core.logger.Level
import timber.log.Timber

class EasyTimeTrackingApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // replaced with app startup initializers
//        setupTimber()
//        setupKoin()
    }


    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG).d("Debug mode enabled")
        } else {

            //TODO use the commented setup when you have the Crashlytics setup ready
//            Timber.plant(CrashReportingTree())
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG).d("Release mode enabled")
        }
    }


    private fun setupKoin() {
        try {
            startKoin {
                // Updated for Koin 4.x: use Level.INFO or Level.ERROR
                androidContext(this@EasyTimeTrackingApp)
                androidLogger(Level.INFO)
                modules(appModule, databaseModule)
            }
        } catch (_: KoinApplicationAlreadyStartedException) {
            // This catches the error if Koin was started elsewhere,
            // but in the Application class, this is typically the only place.
        }
    }

    companion object {
        private const val TAG = "che.EasyTimeTrackingApp"
    }
}
