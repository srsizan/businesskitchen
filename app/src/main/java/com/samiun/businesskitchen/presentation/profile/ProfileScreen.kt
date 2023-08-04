package com.samiun.businesskitchen.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.samiun.businesskitchen.presentation.sign_in.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val userCollectionRef = Firebase.firestore.collection("users")
    fun saveUserData(user: UserData)  = CoroutineScope(Dispatchers.IO).launch {
        try {
            val queryUsers = userCollectionRef
                .whereEqualTo("email", user.email)
                .get()
                .await()
Timber.d("${queryUsers.documents}")
            if (queryUsers.documents.isEmpty()) {
                userCollectionRef.add(user).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show()
                }
            }else{
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Welcome, ${user.username}", Toast.LENGTH_SHORT).show()
                }
            }

        }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(userData?.profilePictureUrl != null) {
            LaunchedEffect(Unit){
                Timber.d("Launched effect")
                saveUserData(userData)

            }


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
            userData.email?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(onClick = onSignOut) {
            Text(text = "Sign out")
        }
    }
}