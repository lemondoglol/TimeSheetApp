package util

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun generatePDF(context: Context, content: String) {
    // Create a new PdfDocument
    val pdfDocument = PdfDocument()

    // Create a page info with desired attributes
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()

    // Start a page
    val page = pdfDocument.startPage(pageInfo)

    // Write something on the page
    val canvas = page.canvas
    canvas.drawText(content, 80F, 50F, Paint())

    // Finish the page
    pdfDocument.finishPage(page)

    // Create a file to save the PDF
    val file = File(context.cacheDir, "example.pdf")
    if (file.exists()) {
        Log.d("Lemondog", "file exist ${file.path}")
    } else {
        Log.d("Lemondog", "file not exist ${file.path}")
    }

//
//    try {
//        // Write the PDF content to the file
//        val fileOutputStream = FileOutputStream(file)
//        pdfDocument.writeTo(fileOutputStream)
//        fileOutputStream.close()
//
//        println("PDF created successfully at: ${file.absolutePath}")
//    } catch (e: IOException) {
//        e.printStackTrace()
//        println("Error creating PDF: ${e.message}")
//    }

    // Close the document
    pdfDocument.close()
}
