package com.samiun.businesskitchen.ui.screens.addscreen


import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.samiun.businesskitchen.R
import com.samiun.businesskitchen.data.model.Items
import com.samiun.businesskitchen.ui.components.ItemScreenTopBar
import com.samiun.businesskitchen.ui.screens.SharedViewModel
import com.samiun.businesskitchen.util.printPDF
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember {
        mutableStateOf(sharedViewModel.currentScreen)
    }
    var name by remember {
        mutableStateOf(sharedViewModel.selectedItem.name)
    }
    var quantity by remember {
        mutableStateOf(sharedViewModel.selectedItem.quantity.toString())
    }
    var maxUsage by remember {
        mutableStateOf(sharedViewModel.selectedItem.maxUsage.toString())
    }
    var maxStock by remember {
        mutableStateOf(sharedViewModel.selectedItem.maxStock.toString())
    }
    val context = LocalContext.current
    val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            sharedViewModel.selectedItem = Items()
            sharedViewModel.currentData
            navController.navigate(currentScreen)
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
    val calendar = Calendar.getInstance()

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            maxUsage =
                "$selectedYear-${if (selectedMonth + 1 <= 9) "0" else ""}${selectedMonth + 1}-${if (selectedDayOfMonth <= 9) "0" else ""}$selectedDayOfMonth"
        },
        year,
        month,
        dayOfMonth
    )
    val currentDate = Calendar.getInstance()
    datePicker.datePicker.minDate = currentDate.timeInMillis

    datePicker.setOnCancelListener {
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        maxUsage =
            "$currentYear-${if (currentMonth <= 9) "0" else ""}$currentMonth-${if (currentDayOfMonth <= 9) "0" else ""}$currentDayOfMonth"
    }
    Scaffold(
        topBar = {
            ItemScreenTopBar(
                onPrint = {
                    printPDF(
                        context, Items(
                            name = name,
                            quantity = quantity.toInt(),
                            category = currentScreen,
                            maxUsage = maxUsage,
                            maxStock = maxStock.toInt()
                        )
                    )
                }
            )
        }
    ) {
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = name,
                    onValueChange = { name = it },
                    maxLines = 1,
                    placeholder = { Text(text = "Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = if (quantity == "0") "" else quantity,
                    onValueChange = { quantity = it },
                    maxLines = 1,
                    placeholder = { Text(text = "Quantity") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
                fun showDatePicker(){
                    datePicker.show()
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    modifier = Modifier
                        .width(300.dp)
                        .clickable {
                            datePicker.show()
                        },
                    enabled = false,
                    value = if (maxUsage == "0") "" else maxUsage,
                    onValueChange = { maxUsage = it },
                    maxLines = 1,
                    placeholder = { Text(text = "Max Usage") },
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.width(300.dp),
                    value = if (maxStock == "0") "" else maxStock,
                    onValueChange = { maxStock = it },
                    maxLines = 1,
                    placeholder = { Text(text = "Maximum Stock") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {
                    if (name.isNotEmpty() && quantity.toIntOrNull() != null && maxUsage.toIntOrNull() != null && maxStock.toIntOrNull() != null) {
                        sharedViewModel.addItem(
                            Items(
                                name = name,
                                quantity = quantity.toInt(),
                                category = currentScreen,
                                maxUsage = maxUsage,
                                maxStock = maxStock.toInt()
                            )
                        )
                        sharedViewModel.selectedItem = Items()
                        navController.navigate(currentScreen)
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
}
