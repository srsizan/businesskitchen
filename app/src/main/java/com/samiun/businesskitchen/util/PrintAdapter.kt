package com.samiun.businesskitchen.util

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.pdf.PrintedPdfDocument
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.FileOutputStream
import java.io.IOException

class HelloWorldPrintAdapter(private val context: Context) : PrintDocumentAdapter() {

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback,
        extras: Bundle?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            callback.onLayoutCancelled()
            return
        }

        val printDocumentInfo = PrintDocumentInfo.Builder("hello_world.pdf")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .build()

        callback.onLayoutFinished(printDocumentInfo, true)
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback
    ) {
        val pdfDocument = PrintedPdfDocument(context, PrintAttributes.Builder().build())

        try {
            val pageInfo = PdfDocument.PageInfo.Builder(300, 300, 1).create()
            val page = pdfDocument.startPage(pageInfo)

            val canvas = page.canvas
            val paint = Paint()
            paint.color = Color.BLACK
            canvas.drawText("Hello, World!", 100f, 100f, paint)

            pdfDocument.finishPage(page)

            val outputStream = FileOutputStream(destination.fileDescriptor)
            pdfDocument.writeTo(outputStream)

            callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        } catch (e: IOException) {
            callback.onWriteFailed(e.toString())
        } finally {
            pdfDocument.close()
        }
    }
}
