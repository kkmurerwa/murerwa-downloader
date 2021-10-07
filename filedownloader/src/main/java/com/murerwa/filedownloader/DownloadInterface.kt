package com.murerwa.filedownloader

import android.util.Log

const val TAG = "MURERWA_DOWNLOADER"

interface DownloadInterface {
    fun onDownloadProgressChanged(newProgress: Int) {
        Log.d(TAG, "DownloadProgress - $newProgress")
    }

    fun onErrorOccurred(error: String) {
        // Default implementation
        Log.d(TAG, "Error - $error")
    }

    fun onDownloadStarted() {
        // Default implementation
        Log.d(TAG, "Message - File Download started")
    }

    fun onDownloadCompleted() {
        // Default implementation
        Log.d(TAG, "Success - File Download completed")
    }
}