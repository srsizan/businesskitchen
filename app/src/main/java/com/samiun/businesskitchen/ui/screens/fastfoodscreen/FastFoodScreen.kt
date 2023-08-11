package com.samiun.businesskitchen.ui.screens.fastfoodscreen

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samiun.businesskitchen.ui.components.ItemCard
import com.samiun.businesskitchen.ui.components.ItemsFloatingActionButton
import com.samiun.businesskitchen.ui.screens.Screen
import com.samiun.businesskitchen.ui.screens.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FastFoodScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val fastFood = sharedViewModel.getItems()?.data?.fastFoodItems
    val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navController.navigate(Screen.HomeScreen.route)
        }
    }
    val localOnBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current

    DisposableEffect(Unit) {
        val dispatcher = localOnBackPressedDispatcherOwner?.onBackPressedDispatcher
        dispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        "Fast Foods", color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        },
        floatingActionButton = {
            sharedViewModel.currentScreen = Screen.FastFoodScreen.route
            ItemsFloatingActionButton(
                navController = navController,
                sharedViewModel,
                Screen.AddScreen.route
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {
                if (fastFood != null) {
                    items(fastFood.size) { index ->
                        ItemCard(name = fastFood[index].name) {

                        }
                    }
                }
            }
        }
    }
}
