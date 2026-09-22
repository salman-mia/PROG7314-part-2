package com.example.campusconnect.auth

import android.content.Context

class TokenManager(context: Context) {

    private val preferences = context.getSharedPreferences(
        "campus_connect_auth",
        Context.MODE_PRIVATE
    )

    fun saveToken(token: String) {
        preferences.edit()
            .putString("jwt_token", token)
            .apply()
    }

    fun getToken(): String? {
        return preferences.getString("jwt_token", null)
    }

    fun clearToken() {
        preferences.edit()
            .remove("jwt_token")
            .apply()
    }

    fun hasToken(): Boolean {
        return !getToken().isNullOrBlank()
    }
}