package com.example.campusconnect.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.campusconnect.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*
 * Creates one DataStore instance for saving settings.
 */
private val Context.dataStore by preferencesDataStore(
    name = "campus_settings"
)

/**
 * Saves and retrieves the preferences selected by the user.
 *
 * DataStore ensures that preferences remain saved when the
 * application is closed and opened again.
 */
class SettingsRepository(
    private val context: Context
) {

    companion object {
        private const val TAG = "SettingsRepository"

        private val NOTIFICATIONS_ENABLED =
            booleanPreferencesKey("notifications_enabled")

        private val EVENT_NOTIFICATIONS_ENABLED =
            booleanPreferencesKey("event_notifications_enabled")

        private val SELECTED_LANGUAGE =
            stringPreferencesKey("selected_language")
    }

    /**
     * Continuously provides the latest saved settings.
     */
    val settings: Flow<UserSettings> =
        context.dataStore.data.map { preferences ->

            UserSettings(
                notificationsEnabled =
                    preferences[NOTIFICATIONS_ENABLED] ?: true,

                eventNotifications =
                    preferences[EVENT_NOTIFICATIONS_ENABLED] ?: true,

                language =
                    preferences[SELECTED_LANGUAGE] ?: "English"
            )
        }

    /**
     * Saves the general notification preference.
     */
    suspend fun setNotificationsEnabled(
        enabled: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = enabled
        }

        Log.i(
            TAG,
            "General notification preference saved: $enabled"
        )
    }

    /**
     * Saves the event notification preference.
     */
    suspend fun setEventNotificationsEnabled(
        enabled: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[EVENT_NOTIFICATIONS_ENABLED] = enabled
        }

        Log.i(
            TAG,
            "Event notification preference saved: $enabled"
        )
    }

    /**
     * Saves the language selected by the user.
     */
    suspend fun setLanguage(
        language: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_LANGUAGE] = language
        }

        Log.i(
            TAG,
            "Language preference saved: $language"
        )
    }
}