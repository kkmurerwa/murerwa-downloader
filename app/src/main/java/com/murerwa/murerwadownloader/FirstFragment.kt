package com.murerwa.murerwadownloader

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.murerwa.filedownloader.DownloadInterface
import com.murerwa.filedownloader.FileDownloader
import com.murerwa.murerwadownloader.databinding.FragmentFirstBinding


class FirstFragment : Fragment(R.layout.fragment_first), DownloadInterface {
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

//    private val url = "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0f/A._Schwarzenegger.jpg/1200px-A._Schwarzenegger.jpg"
//    private val url = "https://parcelle.kuzasystems.com/downloads/releases/1.0.1.15.apk"
    private val url = "http://www.ecomesty.co.ke/kytabu/the-time-machine-by-h.-g.-wells.epub"
//    private val url = "https://murerwa.com/murerwa_cv.pdf"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFirstBinding.bind(view)

        binding.buttonFirst.setOnClickListener {
            downloadViaInterface()
        }
    }

    private fun downloadViaInterface() {
        val fileDownloader = FileDownloader(
            downloadLink = url,
            context = requireActivity(),
            fileName = "test.epub",
            downloadInterface = this
        )

        fileDownloader.downloadFile()
    }

    override fun onDownloadProgressChanged(newProgress: Int) {
        binding.progressBar.progress = newProgress
        binding.textView.text = "$newProgress%"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}