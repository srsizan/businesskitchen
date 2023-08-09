package com.samiun.businesskitchen.ui.screens.cakescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.samiun.businesskitchen.ui.components.ItemsFloatingActionButton
import com.samiun.businesskitchen.ui.screens.Screen
import com.samiun.businesskitchen.ui.screens.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CakeScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val cakes = sharedViewModel.getCake()?.data?.cakeItems
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        "Cakes", color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        },
        floatingActionButton = {
            ItemsFloatingActionButton(navController = navController, sharedViewModel, Screen.AddCakeScreen.route)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Text(text = cakes.toString())
        }
    }
}