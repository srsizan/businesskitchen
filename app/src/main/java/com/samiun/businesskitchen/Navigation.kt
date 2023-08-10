package com.samiun.businesskitchen

import GoogleAuthUiClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.samiun.businesskitchen.ui.screens.Screen
import com.samiun.businesskitchen.ui.screens.SharedViewModel
import com.samiun.businesskitchen.ui.screens.cakescreen.AddCakeScreen
import com.samiun.businesskitchen.ui.screens.cakescreen.CakeScreen
import com.samiun.businesskitchen.ui.screens.consumergoodscreen.AddConsumerGoodSceen
import com.samiun.businesskitchen.ui.screens.consumergoodscreen.ConsumerGoodScreen
import com.samiun.businesskitchen.ui.screens.fastfoodscreen.AddFastFoodScreen
import com.samiun.businesskitchen.ui.screens.fastfoodscreen.FastFoodScreen
import com.samiun.businesskitchen.ui.screens.homescreen.HomeScreen
import com.samiun.businesskitchen.ui.screens.miscscreen.AddMiscScreen
import com.samiun.businesskitchen.ui.screens.miscscreen.MiscScreen
import com.samiun.businesskitchen.ui.screens.signinscreen.SignInScreen
import com.samiun.businesskitchen.ui.screens.signinscreen.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun Navigation() {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
    NavHost(navController = navController, startDestination = Screen.SigningScreen.route) {
        composable(Screen.SigningScreen.route) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate(Screen.HomeScreenLoggedIn.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == ComponentActivity.RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate(Screen.HomeScreenLoggedIn.route)
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    scope.launch {
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

        composable(Screen.HomeScreenLoggedIn.route) {
            HomeScreen(
                isLoggedIn = true,
                navController = navController,
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    }
                },
                sharedViewModel = sharedViewModel
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    }
                },
                sharedViewModel = sharedViewModel
            )
        }

        composable(route = Screen.CakeScreen.route) {
            CakeScreen(navController, sharedViewModel)
        }
        composable(route = Screen.AddCakeScreen.route) {
            AddCakeScreen(navController, sharedViewModel)
        }
        composable(route = Screen.ConsumerGoodsScreen.route) {
            ConsumerGoodScreen(navController, sharedViewModel)
        }
        composable(route = Screen.AddConsumerGoodsScreen.route) {
            AddConsumerGoodSceen(navController, sharedViewModel)
        }
        composable(route = Screen.MiscScreen.route) {
            MiscScreen(navController, sharedViewModel)
        }
        composable(route = Screen.AddMiscScreen.route) {
            AddMiscScreen(navController, sharedViewModel)
        }
        composable(route = Screen.FastFoodScreen.route) {
            FastFoodScreen(navController, sharedViewModel)
        }
        composable(route = Screen.AddFastFoodScreen.route) {
            AddFastFoodScreen(navController, sharedViewModel)
        }
    }
}