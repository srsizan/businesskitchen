package com.samiun.businesskitchen
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samiun.businesskitchen.presentation.profile.ProfileScreen
import com.samiun.businesskitchen.presentation.sign_in.GoogleAuthUiClient
import com.samiun.businesskitchen.presentation.sign_in.SignInScreen
import com.samiun.businesskitchen.presentation.sign_in.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
@Composable
fun Navigation(googleAuthUiClient: GoogleAuthUiClient) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "sign_in") {
        composable("sign_in") {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = Unit) {
                if(googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate("profile")
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == ComponentActivity.RESULT_OK) {
                        GlobalScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if(state.isSignInSuccessful) {
                    navController.navigate("profile")
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    GlobalScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        Timber.e("$signInIntentSender")

                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }
        composable("profile") {
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    GlobalScope.launch {
                        googleAuthUiClient.signOut()
                        withContext(Dispatchers.Main){
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    }
}
