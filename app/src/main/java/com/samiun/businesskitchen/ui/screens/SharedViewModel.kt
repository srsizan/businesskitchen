package com.samiun.businesskitchen.ui.screens

import androidx.lifecycle.ViewModel
import com.samiun.businesskitchen.repository.BusinessKitchenRepository
import com.samiun.businesskitchen.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val businessKitchenRepository: BusinessKitchenRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
}