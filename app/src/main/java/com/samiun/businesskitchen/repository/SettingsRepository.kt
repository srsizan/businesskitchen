package com.samiun.businesskitchen.repository

import android.content.SharedPreferences
import com.samiun.businesskitchen.util.Constants.AUTH_TOKEN
import com.samiun.businesskitchen.util.Constants.IS_LOGGED_IN
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsRepository @Inject constructor(private val prefs: SharedPreferences) {
    fun setIsLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun getIsLoggedIn(): Boolean = prefs.getBoolean(IS_LOGGED_IN, false)

    fun setAuthToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN, "Token $token").apply()
    }

    fun getAuthToken() = prefs.getString(AUTH_TOKEN, "") ?: ""
}