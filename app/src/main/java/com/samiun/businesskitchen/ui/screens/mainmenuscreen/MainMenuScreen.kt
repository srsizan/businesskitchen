package com.samiun.businesskitchen.ui.screens.mainmenuscreen

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.samiun.businesskitchen.ui.screens.SharedViewModel
import com.samiun.businesskitchen.ui.screens.loginscreen.LoginViewModel
import com.samiun.businesskitchen.ui.screens.loginscreen.UserData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    navController: NavController,
    viewModel: SharedViewModel,
    modifier: Modifier = Modifier,
    userData: UserData?,
    onSignOut: () -> Unit
) {
    var exitDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val localOnBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
    val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exitDialog = true
        }
    }
    DisposableEffect(Unit) {
        val dispatcher = localOnBackPressedDispatcherOwner?.onBackPressedDispatcher
        dispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var errMsg by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

}