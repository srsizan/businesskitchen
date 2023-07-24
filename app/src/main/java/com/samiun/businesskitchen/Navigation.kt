package com.samiun.businesskitchen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samiun.businesskitchen.ui.Screen
import com.samiun.businesskitchen.ui.screens.SharedViewModel
import com.samiun.businesskitchen.ui.screens.loginscreen.LoginScreen
import com.samiun.businesskitchen.ui.screens.loginscreen.LoginViewModel
import com.samiun.businesskitchen.ui.screens.mainmenuscreen.MainMenuScreen

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
            LoginScreen(navController, loginViewModel)
        }
        composable(route = Screen.MainMenuScreen.route) {
            MainMenuScreen(navController, loginViewModel)
        }

    }
}