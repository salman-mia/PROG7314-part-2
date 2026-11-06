package com.example.campusconnect

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import java.util.Locale

/**
 * Applies and remembers the language selected by the user.
 *
 * English uses the "en" language code.
 * isiZulu uses the "zu" language code.
 */
object LocaleHelper {

    private const val TAG = "CampusLanguage"
    private const val PREFERENCES_NAME = "campus_language"
    private const val LANGUAGE_KEY = "selected_language"
    private const val DEFAULT_LANGUAGE = "en"

    /**
     * Retrieves the language previously selected by the user.
     */
    fun getSavedLanguage(context: Context): String {
        val preferences = context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

        return preferences.getString(
            LANGUAGE_KEY,
            DEFAULT_LANGUAGE
        ) ?: DEFAULT_LANGUAGE
    }

    /**
     * Saves the language code so that it remains selected when
     * the application is closed and opened again.
     */
    fun saveLanguage(
        context: Context,
        languageCode: String
    ) {
        context.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        ).edit()
            .putString(LANGUAGE_KEY, languageCode)
            .apply()

        Log.i(
            TAG,
            "Application language saved: $languageCode"
        )
    }

    /**
     * Creates a context using the saved application language.
     */
    fun wrapContext(context: Context): Context {
        val languageCode = getSavedLanguage(context)
        val locale = Locale.forLanguageTag(languageCode)

        Locale.setDefault(locale)

        val configuration = Configuration(
            context.resources.configuration
        )

        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        Log.i(
            TAG,
            "Application language applied: $languageCode"
        )

        return context.createConfigurationContext(configuration)
    }
}