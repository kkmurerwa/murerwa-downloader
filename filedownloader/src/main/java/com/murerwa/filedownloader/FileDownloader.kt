package com.murerwa.filedownloader

import android.Manifest
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import android.content.ContextWrapper
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi

class FileDownloader(
    private val downloadLink: String,
    private val context: Context,
    private val fileName: String,
    private var filePath: String = context.filesDir.toString(),
    private var activity: Activity,
    private val downloadInterface: DownloadInterface
) {
    fun downloadFile() {
        if (filePath == context.filesDir.toString()) {
            executeDownload()
        } else {
            filePath = "storage/emulated/0/$filePath"

            requestStoragePermission()
        }
    }

    private fun executeDownload() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // Create directory if does not exist
                File(filePath).mkdirs()

                val url = URL(downloadLink)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doOutput = true
                connection.doInput = true
                connection.connect()

                val fileLength = connection.contentLength

                if (fileLength < 0) {
                    withContext(Dispatchers.Main) {
                        downloadInterface.onErrorOccurred("We encountered an error downloading the file. The file might be unreachable.")
                    }

                    return@launch
                }

                val outputFile = File(filePath, fileName)
                val fos = FileOutputStream(outputFile)

                val inputStream = if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    connection.errorStream
                } else {
                    connection.inputStream
                }

                if (inputStream == connection.errorStream) {
                    withContext(Dispatchers.Main) {
                        downloadInterface.onErrorOccurred("It looks like the passed link does not exist")
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        downloadInterface.onDownloadStarted()
                    }

                    val buffer = ByteArray(4096)
                    var len1: Int
                    var total = 0
                    while (inputStream.read(buffer).also { len1 = it } != -1) {
                        total += len1
                        val progress = (total * 100)/fileLength

                        fos.write(buffer, 0, len1)

                        withContext(Dispatchers.Main) {
                            // Update progress interface
                            downloadInterface.onDownloadProgressChanged(progress)
                        }
                    }
                    fos.close()
                    inputStream.close()

                    withContext(Dispatchers.Main) {
                        downloadInterface.onDownloadCompleted()
                    }
                }


            } catch (ioException: IOException) {
                withContext(Dispatchers.Main) {
                    downloadInterface.onErrorOccurred(ioException.stackTraceToString())
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    downloadInterface.onErrorOccurred(exception.stackTraceToString())
                }
            }
        }
    }

    fun checkIfFileExists(): Boolean {
        File(filePath).mkdirs()

        val filePathName = File(filePath, fileName)

        return filePathName.exists()
    }

    fun getFilePath(): String {
        return filePath
    }

    private fun requestStoragePermission() {
        val applicationContext = context.applicationContext

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                // No need to do anything
                Log.d("READ EXTERNAL STORAGE", "Permission granted")

                executeDownload()
            }
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                // No need to do anything
                Log.d("WRITE EXTERNAL STORAGE", "Permission granted")

                executeDownload()
            }
            else -> {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )
            }
        }
    }
}