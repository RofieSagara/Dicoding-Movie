package com.sagara.dicodingmovie.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("Private", MODE_PRIVATE)

    companion object {
        private const val LANGUAGE = "language"
        private const val RELEASE_REMINDER = "release_reminder"
        private const val DAILY_REMINDER = "daily_reminder"
    }

    var language: String
        get() = sharedPreferences.getString(LANGUAGE, "en") ?: "en"
        set(value) {
            sharedPreferences.edit().putString(LANGUAGE, value).also { it.apply() }
        }

    var isRealeaseReminder: Boolean
        get() = sharedPreferences.getBoolean(RELEASE_REMINDER, false)
        set(value) {
            sharedPreferences.edit().putBoolean(RELEASE_REMINDER, value).also { it.apply() }
        }

    var isDailyReminder: Boolean
        get() = sharedPreferences.getBoolean(DAILY_REMINDER, false)
        set(value) {
            sharedPreferences.edit().putBoolean(DAILY_REMINDER, value).also { it.apply() }
        }
}