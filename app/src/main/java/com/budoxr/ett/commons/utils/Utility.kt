/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See the LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.commons.utils

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.withTimeout
import org.koin.core.component.KoinComponent
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

class Utility(private val context: Context): KoinComponent {
    /**
     * Formats a long value to show only the last three digits,
     * padded with leading zeros if the value is less than 100.
     */
    fun formatLastThreeDigits(value: Long): String {
        // Get the last 3 digits using modulo 1000
        val lastThree = value % 1000
        // Format with leading zeros, minimum 3 characters
        return "%03d".format(lastThree)
    }

    /**
     * Programmatically restarts the application.
     * This is required because the Singleton database instance must be re-initialized.
     */
    fun triggerAppRestart() {
        try {
            val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
            if (intent != null) {
                val mainIntent = Intent.makeRestartActivityTask(intent.component)
                context.startActivity(mainIntent)
                Thread.sleep(200)
            }
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        }
    }

    @Throws(Exception::class)
    suspend fun <T> performAsyncOperation(
        scope: CoroutineScope,
        timeout: Long,
        timeUnit: TimeUnit,
        dispatcher: CoroutineDispatcher,
        operation: suspend() -> T
    ): Deferred<T> = scope.async(dispatcher) {
        withTimeout(timeUnit.toMillis(timeout).milliseconds) {
            operation()
        }
    }
}
