package com.samiun.businesskitchen

import android.content.Context
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.firebase.FirebaseApp
import com.samiun.businesskitchen.ui.theme.BusinessKitchenTheme
import com.samiun.businesskitchen.util.HelloWorldPrintAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this@MainActivity)
        setContent {
            BusinessKitchenTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    printPDF()
                    Navigation()
                }
            }
        }
    }

    fun printPDF() {
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager

        try {
            val printAdapter = HelloWorldPrintAdapter(this)

            //val printAdapter = PdfDocumentAdapter(this,Comman.getAppPath(this)+file_name)
            printManager.print("Documents",printAdapter, PrintAttributes.Builder().build())
        }catch (e:Exception){
            Timber.tag("Harshita").e("" + e.message)
        }

    }
}