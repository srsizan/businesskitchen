package com.samiun.businesskitchen.ui.screens.homescreen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.samiun.businesskitchen.ui.screens.homescreen.HomeScreenContants.CAN_ACCESS
import com.samiun.businesskitchen.ui.screens.homescreen.HomeScreenContants.USER_ID
import com.samiun.businesskitchen.ui.screens.signinscreen.UserData
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    userData: UserData?,
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val dataBase = Firebase.firestore.collection(stringResource(R.string.users))
    var dialogBox by rememberSaveable {
        mutableStateOf(false)
    }
    val listofItems = listOf(
        Pair("Cake", R.drawable.cake),
        Pair("Consumer Goods", R.drawable.consumergoods),
        Pair("Misc", R.drawable.misc),
        Pair("Fast Food", R.drawable.fastfood)
    )
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
                        java.lang.System.exit(0)
                        dialogBox = false

                    }
                ) {
                    Text(text = stringResource(R.string.okay))
                }
            },
        )
    }

    Scaffold(topBar = {
        HomeScreenTopBar(
            onSignOut = onSignOut
        )
    }) {
        Box(modifier = modifier.padding(it)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(listofItems) { foodItems ->
                        Card(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(10.dp)
                                .combinedClickable(
                                    onClick = {
                                        navController.navigate(foodItems.first)
                                    },
                                    onLongClick = {
                                    }
                                )
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = foodItems.second,
                                    contentDescription = stringResource(R.string.imagedescription),
                                    contentScale = ContentScale.Crop
                                )
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
