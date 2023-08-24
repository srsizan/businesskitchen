package com.samiun.businesskitchen.util

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.pdf.PrintedPdfDocument
import com.samiun.businesskitchen.data.model.Items
import java.io.FileOutputStream
import java.io.IOException

class PrintAdapter(private val context: Context, items: Items) : PrintDocumentAdapter() {
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
            val typeface = Typeface.create("times new roman", Typeface.NORMAL)
            paint.typeface = typeface

            val namePaint = Paint()
            namePaint.color = Color.BLACK
            namePaint.textSize = 20f
            val nametypeface = Typeface.create("times new roman", Typeface.BOLD)
            namePaint.typeface = nametypeface

            val otherPaint = Paint()
            otherPaint.color = Color.BLACK
            otherPaint.textSize = 10f
            val othertypeface = Typeface.create("times new roman", Typeface.NORMAL)
            otherPaint.typeface = othertypeface

            val tableWidth = pageInfo.pageWidth
            val cellWidth = tableWidth / 3
            val cellHeight = 30f
            var startY = 100f
            canvas.drawText(item.name, 20f, 40f, namePaint)

            canvas.drawRect(0.5f, startY, tableWidth.toFloat(), startY + cellHeight, paint)
            canvas.drawText(
                "Category",
                (cellWidth / 2).toFloat(),
                startY + cellHeight / 2,
                otherPaint
            )
            canvas.drawText(
                "Max Usage",
                (cellWidth + cellWidth / 2).toFloat(), startY + cellHeight / 2, otherPaint
            )

            startY += cellHeight

            canvas.drawRect(0.5f, startY, tableWidth.toFloat(), startY + cellHeight, paint)
            canvas.drawText(
                item.category,
                (cellWidth / 2).toFloat(),
                startY + cellHeight / 2,
                otherPaint
            )
            otherPaint.color = Color.RED
            canvas.drawText(
                item.maxUsage,
                (cellWidth + cellWidth / 2).toFloat(), startY + cellHeight / 2, otherPaint
            )
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
