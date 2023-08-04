package com.samiun.businesskitchen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.samiun.businesskitchen.presentation.sign_in.GoogleAuthUiClient
import com.samiun.businesskitchen.ui.theme.BusinessKitchenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this@MainActivity)
        setContent {
            BusinessKitchenTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                     val googleAuthUiClient by lazy {
                        GoogleAuthUiClient(
                            context = applicationContext,
                            oneTapClient = Identity.getSignInClient(applicationContext)
                        )
                    }
                    val navController = rememberNavController()
                    Navigation(googleAuthUiClient = googleAuthUiClient)
                    lifecycleScope
                }
            }
        }
    }
}