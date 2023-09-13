package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.api.SettingsRepository
import com.example.playlistmaker.presentation.api.SettingsInteractor

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository): SettingsInteractor {
    override fun saveDarkThemeValue(value: Boolean) {
        settingsRepository.saveDarkThemeValue(value)
    }
}