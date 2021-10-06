package com.murerwa.filedownloader

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FileDownloader(
    private val downloadLink: String,
    private val context: Context,
    private val fileName: String,
    private var filePath: File = context.filesDir,
    private val downloadInterface: DownloadInterface
) {

    fun downloadToDirectory() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Downloading file", Toast.LENGTH_LONG).show()
                }

                // Create directory if does not exist
                filePath.mkdirs()

                val url = URL(downloadLink)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doOutput = true
                connection.doInput = true
                connection.connect()

                Log.d("LOG_TAG", "PATH: $filePath")

                val fileLength = connection.contentLength

                Log.d("FILE LENGTH", fileLength.toString())

                Log.v("LOG_TAG", "PATH: $filePath")

                val outputFile = File(filePath, "eparcel.epub")
                val fos = FileOutputStream(outputFile)

                val inputStream = if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    connection.errorStream
                } else {
                    connection.inputStream
                }

                if (inputStream == connection.errorStream) {
                    Log.d("ERROR","An error occurred")
                } else {
                    val buffer = ByteArray(4096)
                    var len1: Int
                    var total = 0
                    while (inputStream.read(buffer).also { len1 = it } != -1) {
                        total += len1
                        val progress = (total * 100)/fileLength
                        Log.d("DOWNLOAD PROGRESS", progress.toString())
                        fos.write(buffer, 0, len1)

                        withContext(Dispatchers.Main) {
                            // Update progress interface
                            downloadInterface.onDownloadProgressChanged(progress)
                        }
                    }
                    fos.close()
                    inputStream.close()

                    Log.d("OutputStream", connection.toString())

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Download completed", Toast.LENGTH_LONG).show()
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}