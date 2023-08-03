package com.samiun.businesskitchen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samiun.businesskitchen.presentation.sign_in.SignInViewModel
import com.samiun.businesskitchen.ui.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

//            LogInScreen(
//                state = state,
//                onSignInClick = {
//                    lifecycleScope.launch {
//                        val signInIntentSender = googleAuthUiClient.signIn()
//                        launcher.launch(
//                            IntentSenderRequest.Builder(
//                                signInIntentSender ?: return@launch
//                            ).build()
//                        )
//                    }
//                }
//            )
        }
        composable(route = Screen.MainMenuScreen.route) {
           // MainMenuScreen(navController, sharedViewModel)
        }
    }
}