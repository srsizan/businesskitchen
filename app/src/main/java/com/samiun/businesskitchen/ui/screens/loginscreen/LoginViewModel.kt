package com.samiun.businesskitchen.ui.screens.loginscreen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samiun.businesskitchen.repository.BusinessKitchenRepository
import com.samiun.businesskitchen.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: BusinessKitchenRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    var isLoggedIn = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var loginError = mutableStateOf("")
    var currentUserRole = mutableStateOf("")

    init {
        isLoggedIn.value = settingsRepository.getIsLoggedIn()
    }

    fun loginRequest(body: LoginModel, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postLogin(body)
        }
    }


    fun logout() {

    }
}