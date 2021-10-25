package com.murerwa.murerwadownloader

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.murerwa.filedownloader.DownloadInterface
import com.murerwa.filedownloader.FileDownloader
import com.murerwa.murerwadownloader.databinding.FragmentFirstBinding
import java.io.File


class FirstFragment : Fragment(R.layout.fragment_first), DownloadInterface {
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val url = "http://www.ecomesty.co.ke/kytabu/the-time-machine-by-h.-g.-wells.epub"

    private val fileDownloader by lazy {
        FileDownloader(
            downloadLink = url,
            context = requireActivity(),
            fileName = "test.epub",
            filePath = "Download",
            activity = requireActivity(),
            downloadInterface = this
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFirstBinding.bind(view)

        binding.buttonFirst.setOnClickListener {
            Log.d("FILE PATH", fileDownloader.getFilePath())

            downloadViaInterface()
        }
    }

    private fun downloadViaInterface() {
        fileDownloader.downloadFile()
    }

    override fun onDownloadProgressChanged(newProgress: Int) {
        super.onDownloadProgressChanged(newProgress)
        binding.progressBar.progress = newProgress
        binding.textView.text = "$newProgress%"
    }

    override fun onDownloadStarted() {
        super.onDownloadStarted()
        Toast.makeText(context, "Downloading started", Toast.LENGTH_SHORT).show()
    }

    override fun onDownloadCompleted() {
        super.onDownloadCompleted()
        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show()
    }

    override fun onErrorOccurred(error: String) {
        super.onErrorOccurred(error)
        Toast.makeText(context, "Sorry. The download could not be completed", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}