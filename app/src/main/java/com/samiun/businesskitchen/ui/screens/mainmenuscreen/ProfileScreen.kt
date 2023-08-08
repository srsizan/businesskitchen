package com.samiun.businesskitchen.ui.screens.mainmenuscreen

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.samiun.businesskitchen.ui.screens.UserData
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.system.exitProcess

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val dataBase = Firebase.firestore.collection("users")
    var dialogBox by rememberSaveable {
        mutableStateOf(false)
    }
    if(dialogBox){
        AlertDialog(
            onDismissRequest = {  },
            text = { Text(text = "User Added, Please wait while administrator let you in") },
            confirmButton = {
                Button(
                    onClick = {
                        val activity = context as Activity
                        activity.finish()
                        java.lang.System.exit(0)
                        dialogBox = false

                    }
                ) {
                    Text(text = "Okay")
                }
            },
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchedEffect(Unit){
            if (userData != null) {
                saveUser(userData,dataBase,context)
            }

            val isUser = dataBase
                .whereEqualTo("userId", userData?.userId)
                .whereEqualTo("canAccess", true)
                .get()
                .await()
            try {
                if(isUser.isEmpty){
                    dialogBox = true

                }else{
                    if (userData != null) {
                        Toast.makeText(context, "Welcome, ${userData.username} We are delighted to have you back", Toast.LENGTH_LONG).show()
                    }
                }
            }catch (e: Exception){
                Timber.e("$e")
            }
        }
        if(userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = onSignOut) {
            Text(text = "Sign out")
        }
    }
}

suspend fun saveUser(userData: UserData, dataBase: CollectionReference, context: Context) {
    val isUser = dataBase
        .whereEqualTo("userId",userData.userId)
        .get()
        .await()
    try {
        if(isUser.isEmpty){
            dataBase.add(userData).await()
            //Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show()

        }else{
            //Toast.makeText(context, "Welcome, ${userData.username}", Toast.LENGTH_SHORT).show()
        }
    }catch (e: Exception){
        Timber.e("$e")
    }
}
