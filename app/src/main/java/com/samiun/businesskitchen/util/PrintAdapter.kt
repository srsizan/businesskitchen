package com.samiun.businesskitchen.util

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
import androidx.compose.ui.unit.sp
import com.samiun.businesskitchen.data.model.Items
import java.io.FileOutputStream
import java.io.IOException

class PrintAdapter(private val context: Context, items : Items) : PrintDocumentAdapter() {
    val item = items

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
        val printAttributes = PrintAttributes.Builder()
            .setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME)
            .setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT)
            .setResolution(PrintAttributes.Resolution("pdf", "pdf", 300, 300))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            .build()

        val pdfDocument = PrintedPdfDocument(context, printAttributes)
        try {
            val pageInfo = PdfDocument.PageInfo.Builder(300, 300, 1).create()
            val page = pdfDocument.startPage(pageInfo)

            val canvas = page.canvas
            val paint = Paint()
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE // Set the paint style to stroke for drawing borders
            paint.textSize = 10f
            var typeface = Typeface.create("times new roman", Typeface.NORMAL)
            paint.typeface = typeface

            val boldTypeFace = Typeface.create("times new roman", Typeface.BOLD)
            val textPaint = Paint()
            textPaint.color = Color.BLACK
            textPaint.textSize = 10f
            textPaint.typeface = typeface

            val tableWidth = pageInfo.pageWidth
            val cellWidth = tableWidth / 4
            val cellHeight = 30f
            var startX = 50f
            var startY = 100f
            val lineHeight = 40f
            canvas.drawText("name: ${item.name}", 20f, 50f, textPaint.apply {
                this.textSize = 20f
                this.typeface = boldTypeFace
            })

            canvas.drawRect(0f, startY, tableWidth.toFloat(), startY + cellHeight, paint)
            canvas.drawText("Category",
                (cellWidth + cellWidth / 3).toFloat(), startY + cellHeight / 3, textPaint)
            canvas.drawText("Max Usage",
                (cellWidth * 2 + cellWidth / 3).toFloat(), startY + cellHeight / 3, textPaint)

            startY += cellHeight

            canvas.drawRect(0f, startY, tableWidth.toFloat(), startY + cellHeight, paint)
            canvas.drawText(item.category,
                (cellWidth + cellWidth / 2).toFloat(), startY + cellHeight / 2, textPaint)
            canvas.drawText(item.maxUsage.toString(),
                (cellWidth * 2 + cellWidth / 2).toFloat(), startY + cellHeight / 2, textPaint)
            startY += cellHeight
//            canvas.drawRect(0f, startY, tableWidth.toFloat(), startY + cellHeight, paint)
//            canvas.drawText("name: ${item.name}", startX, startY, paint)
//            canvas.drawText("Category:${item.category}", startX, startY+20, paint)
//            canvas.drawText("Max Usage: ${item.maxUsage}", startX, startY+40, paint)
//            startY += lineHeight



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
