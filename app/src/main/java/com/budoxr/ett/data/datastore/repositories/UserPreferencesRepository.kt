/*
 * Copyright (c) 2026 Henry De la Mano
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 */
package com.budoxr.ett.data.datastore.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.budoxr.ett.ui.navigation.Screens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// Delegate to create a single instance of DataStore across the app scope
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class UserPreferencesRepository(private val context: Context) {
    private object PreferencesKeys {
        val LAST_SCREEN = stringPreferencesKey("last_screen")
    }

    val lastScreen: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.LAST_SCREEN] ?: Screens.MonitorScreen.baseRoute
        }

    suspend fun saveLastScreen(baseRoute: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_SCREEN] = baseRoute
        }
    }

}