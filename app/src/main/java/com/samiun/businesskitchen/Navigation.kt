package com.samiun.businesskitchen

import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samiun.businesskitchen.ui.Screen
import com.samiun.businesskitchen.ui.screens.SharedViewModel
import com.samiun.businesskitchen.ui.screens.loginscreen.LogInScreen
import com.samiun.businesskitchen.ui.screens.loginscreen.LoginScreen
import com.samiun.businesskitchen.ui.screens.loginscreen.LoginViewModel
import com.samiun.businesskitchen.ui.screens.loginscreen.SignInViewModel
import com.samiun.businesskitchen.ui.screens.mainmenuscreen.MainMenuScreen
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (!loginViewModel.isLoggedIn.value) Screen.LoginScreen.route else Screen.MainMenuScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LogInScreen(
                state = state,
                onSignInClick = {
                    lifecycleScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }
        composable(route = Screen.MainMenuScreen.route) {
           // MainMenuScreen(navController, sharedViewModel)
        }
    }
}