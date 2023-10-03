package com.example.playlistmaker.data.repository

import android.content.SharedPreferences
import com.example.playlistmaker.domain.api.SettingsRepository

class SettingsRepositoryImpl(private val sharedPreferences: SharedPreferences):SettingsRepository {
    override fun saveDarkThemeValue(value: Boolean) {
        sharedPreferences.edit().putBoolean("isDarkTheme",value).apply()
    }
}