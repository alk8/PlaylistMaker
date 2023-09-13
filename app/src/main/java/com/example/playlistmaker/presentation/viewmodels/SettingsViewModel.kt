package com.example.playlistmaker.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.presentation.api.SettingsInteractor

class SettingsViewModel(private val settingsInteractor: SettingsInteractor): ViewModel() {

    fun saveDarkThemeValue(value:Boolean){
        settingsInteractor.saveDarkThemeValue(value)
    }

}