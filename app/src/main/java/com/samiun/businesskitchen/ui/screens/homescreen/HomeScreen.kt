package com.samiun.businesskitchen.ui.screens.homescreen

import android.app.Activity
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.samiun.businesskitchen.R
import com.samiun.businesskitchen.ui.components.HomeScreenTopBar
import com.samiun.businesskitchen.ui.components.ItemWithImage
import com.samiun.businesskitchen.ui.screens.SharedViewModel
import com.samiun.businesskitchen.ui.screens.homescreen.HomeScreenContants.CAN_ACCESS
import com.samiun.businesskitchen.ui.screens.homescreen.HomeScreenContants.USER_ID
import com.samiun.businesskitchen.ui.screens.signinscreen.UserData
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    isLoggedIn: Boolean = false,
    userData: UserData?,
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current
    val dataBase = Firebase.firestore.collection(stringResource(R.string.users))
    var dialogBox by rememberSaveable {
        mutableStateOf(false)
    }
    var controlPanelBox by rememberSaveable {
        mutableStateOf(false)
    }

    val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val activity = context as Activity
            activity.finish()
            System.exit(0)
            dialogBox = false
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
    val listofItems = listOf(
        Pair("Cake", context.resources.getResourceEntryName(R.drawable.cake)),
        Pair("Consumer Goods", context.resources.getResourceEntryName(R.drawable.consumergoods)),
        Pair("Misc", context.resources.getResourceEntryName(R.drawable.misc)),
        Pair("Fast Food", context.resources.getResourceEntryName(R.drawable.fastfood))
    )
    var selectedItems by remember {
        mutableStateOf(sharedViewModel.getControlPanelList())
    }
    
    selectedItems = if(selectedItems == null) listofItems else selectedItems
    Timber.d("$selectedItems")

    if (dialogBox) {
        AlertDialog(
            onDismissRequest = { },
            text = { Text(text = stringResource(R.string.userDialogMessage)) },
            confirmButton = {
                Button(
                    onClick = {
                        onSignOut
                        val activity = context as Activity
                        activity.finish()
                        System.exit(0)
                        dialogBox = false

                    }
                ) {
                    Text(text = stringResource(R.string.okay))
                }
            },
        )
    }
    if (controlPanelBox) {
        AlertDialog(
            onDismissRequest = { controlPanelBox = false },
            title = { Text(text = "Control Panel") },
            text = {
                Column {
                    for (string in listofItems) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedItems!!.contains(string),
                                modifier = Modifier.padding(0.dp),
                                onCheckedChange = { checked ->
                                    selectedItems = if (checked) {
                                        selectedItems!! + string
                                    } else {
                                        selectedItems!! - string
                                    }
                                }
                            )
                            Text(
                                text = string.first
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        controlPanelBox = false
                        selectedItems?.let { sharedViewModel.addItemsFromControlPanel(it) }

                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        controlPanelBox = false
                    }
                ) {
                    Text(text = "Dismiss")
                }
            }
        )
    }

    Scaffold(topBar = {
        HomeScreenTopBar(
            onSignOut = onSignOut,
            onControlPanelClick = {
                controlPanelBox = true
            }
        )
    }) {
        Box(modifier = modifier.padding(it)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoggedIn) {
                    LaunchedEffect(Unit) {
                        if (userData != null) {
                            saveUser(userData, dataBase)
                        }

                        val isUser = dataBase
                            .whereEqualTo(USER_ID, userData?.userId)
                            .whereEqualTo(CAN_ACCESS, true)
                            .get()
                            .await()
                        try {
                            if (isUser.isEmpty) {
                                dialogBox = true

                            } else {
                                if (userData != null) {
                                    Toast.makeText(
                                        context,
                                        "Welcome, ${userData.username} We are delighted to have you back",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } catch (e: Exception) {
                            Timber.e("$e")
                        }
                    }
                }
                if (selectedItems != null) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        Timber.e("$selectedItems")
                        items(selectedItems!!) { foodItems ->
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column {
                                        ItemWithImage(
                                            context = context,
                                            name = foodItems.first,
                                            image = foodItems.second,
                                            modifier = modifier
                                        ) {
                                            navController.navigate(foodItems.first)
                                        }
                                    }
                                }

                        }
                    }
                }
            }
        }
    }
}

suspend fun saveUser(userData: UserData, dataBase: CollectionReference) {
    val isUser = dataBase
        .whereEqualTo(USER_ID, userData.userId)
        .get()
        .await()
    try {
        if (isUser.isEmpty) {
            dataBase.add(userData).await()
        }
    } catch (e: Exception) {
        Timber.e("$e")
    }
}
