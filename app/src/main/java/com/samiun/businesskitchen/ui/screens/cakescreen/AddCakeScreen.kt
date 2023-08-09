package com.samiun.businesskitchen.ui.screens.cakescreen

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samiun.businesskitchen.R
import com.samiun.businesskitchen.data.model.Items
import com.samiun.businesskitchen.ui.screens.Screen
import com.samiun.businesskitchen.ui.screens.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCakeScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    var name by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf(0)
    }
    var maxUsage by remember {
        mutableStateOf(0)
    }
    var maxStock by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
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
                value = quantity.toString(),
                onValueChange = { quantity = it.toIntOrNull() ?: 0 },
                maxLines = 1,
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.width(300.dp),
                value = maxUsage.toString(),
                onValueChange = { maxUsage = it.toIntOrNull() ?: 0 },
                maxLines = 1,
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.width(300.dp),
                value = maxStock.toString(),
                onValueChange = { maxStock = it.toIntOrNull() ?: 0 },
                maxLines = 1,
                placeholder = { Text(text = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (name.isNotEmpty() && quantity != 0 && maxUsage != 0 && maxStock != 0) {
                    sharedViewModel.addCake(
                        Items(
                            name = name,
                            quantity = quantity,
                            category = "Cake",
                            maxUsage = maxUsage,
                            maxStock = maxStock
                        ), context = context
                    )
                    navController.navigate(Screen.CakeScreen.route)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.enter_proper_items_details),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(text = stringResource(R.string.saveCake))

            }
        }
    }
}
