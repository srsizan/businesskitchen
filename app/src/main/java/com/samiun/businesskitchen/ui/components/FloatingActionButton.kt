package com.samiun.businesskitchen.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.samiun.businesskitchen.ui.screens.SharedViewModel

@Composable
fun ItemsFloatingActionButton(
    navController: NavController, sharedViewModel: SharedViewModel, route: String
) {
    FloatingActionButton(onClick = {
        navController.navigate(route)
    }) {
        Icon(Icons.Default.Add, contentDescription = "Add a Item")
    }
}