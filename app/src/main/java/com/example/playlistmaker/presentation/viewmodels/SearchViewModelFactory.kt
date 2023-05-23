package com.example.playlistmaker.presentation.viewmodels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModelFactory(
    private val sharedPreferences: SharedPreferences,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(sharedPreferences) as T
    }
}