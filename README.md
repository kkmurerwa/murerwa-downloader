# Murerwa Downloader
Murerwa downloader is a custom downloader beside the default Android downloader that helps users download files on the fly and save them to folders that the default Android downloader cannot.

## Supported Android Versions
Murerwa downloader supports all Android versions since `Android 5.0` and `API Level 21`.

## Installing
To install Murerwa Downloader, make sure you have enabled jitpack depositories. Make sure the following dependencies are in your root `settings.gradle` file
    
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }

In your app-level `build.gradle` file, add the following dependency

    dependencies {
        implementation 'com.github.xwaxes01:murerwa-downloader:1.0.0'
    }

## Usage
To use Murerwa Downloader, create an instance of the `FileDownloader` class in the Fragment you want to perform downloads.

    val fileDownloader = FileDownloader(
        downloadLink = url,
        context = requireActivity(),
        fileName = "test.jpeg",
        downloadInterface = this
    )

To start the download, simply call the download function of the class.

    fileDownloader.downloadFile()

To observe the download progress of the library, implement to `DownloadInterface` class in your Fragment.

    class MyFragment : Fragment, DownloadInterface {
        ...
    }

Then implement the `onDownloadProgressChanged` function and perform any UI/UX tasks

    override fun onDownloadProgressChanged(newProgress: Int) {
        // Maybe show the user the download progress
    }

## Contribution
If your would like to contribute to the project, you could complete any of the following tasks;
1. Allow ability to request storage permissions from inside the library