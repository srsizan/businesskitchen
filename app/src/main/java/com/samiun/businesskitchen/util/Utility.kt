package com.samiun.businesskitchen.util

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import com.samiun.businesskitchen.data.model.Items
import timber.log.Timber

fun printPDF(context: Context, items: Items) {
    val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

    try {
        val printAdapter = PrintAdapter(context, items)

        //val printAdapter = PdfDocumentAdapter(this,Comman.getAppPath(this)+file_name)
        printManager.print("Documents", printAdapter, PrintAttributes.Builder().build())
    } catch (e: Exception) {
        Timber.tag("Harshita").e("%s", e.message)
    }

}
