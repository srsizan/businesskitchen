package com.samiun.businesskitchen.ui.screens.consumergoodscreen

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samiun.businesskitchen.R
import com.samiun.businesskitchen.data.model.Items
import com.samiun.businesskitchen.ui.screens.Screen
import com.samiun.businesskitchen.ui.screens.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddConsumerGoodSceen(navController: NavController, sharedViewModel: SharedViewModel, modifier:Modifier = Modifier) {
    var name by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf("")
    }
    var maxUsage by remember {
        mutableStateOf("")
    }
    var maxStock by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navController.navigate(Screen.ConsumerGoodsScreen.route)
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
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.width(300.dp),
                value = name,
                onValueChange = { name = it },
                maxLines = 1,
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.width(300.dp),
                value = quantity,
                onValueChange = { quantity = it },
                maxLines = 1,
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.width(300.dp),
                value = maxUsage,
                onValueChange = { maxUsage = it},
                maxLines = 1,
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.width(300.dp),
                value = maxStock.toString(),
                onValueChange = { maxStock = it },
                maxLines = 1,
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (name.isNotEmpty() && quantity.toIntOrNull() != null && maxUsage.toIntOrNull() != null && maxStock.toIntOrNull() != null) {
                    sharedViewModel.addConsumerGood(
                        Items(
                            name = name,
                            quantity = quantity.toInt(),
                            category = "Consumer Good",
                            maxUsage = maxUsage.toInt(),
                            maxStock = maxStock.toInt()
                        )
                    )
                    navController.navigate(Screen.MiscScreen.route)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.enter_proper_items_details),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(text = stringResource(R.string.saveItem))

            }
        }
    }
}