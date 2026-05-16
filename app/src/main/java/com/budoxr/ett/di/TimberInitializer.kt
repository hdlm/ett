/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.di

import android.content.Context
import androidx.startup.Initializer
import com.budoxr.ett.BuildConfig
import timber.log.Timber

/**
 * Initializes the Timber logging engine at application startup using Jetpack App Startup.
 * Automatically plants a DebugTree only during non-production (debug) builds to prevent
 * leakage of sensitive data logs in production.
 */
class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        // Plant a debug tree configuration safely based on the build type configuration
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG).d("Debug mode enabled")
        }  else {
            //TODO use the commented setup when you have the Crashlytics setup ready
//            Timber.plant(CrashReportingTree())
            Timber.plant(Timber.DebugTree())
            Timber.tag(TAG).d("Release mode enabled")
        }
    }

    // Timber does not depend on any upstream components, making it a root level initializer
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

    companion object {
        private const val TAG = "che.TimberInitializer"
    }
}