/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.di

import android.content.Context
import androidx.startup.Initializer
import com.budoxr.ett.di.Modules.appModule
import com.budoxr.ett.di.Modules.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Initializes the Koin DI framework at application startup using Jetpack App Startup.
 * This replaces explicit initialization code inside the Application subclass.
 */
class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        startKoin {
            // Conforming to modern Koin logger levels
            androidLogger(Level.INFO)
            androidContext(context)
            modules(appModule, databaseModule)
        }
    }

    // Since Koin is the root dependency provider, it has no upstream dependencies
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(TimberInitializer::class.java)
    }
}