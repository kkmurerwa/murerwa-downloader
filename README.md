# Murerwa Downloader
Murerwa downloader is a custom downloader library beside the default Android downloader that helps users download files on the fly and save them to folders that the default Android downloader cannot.

Latest Version: [![Release](https://jitpack.io/v/User/Repo.svg)] (https://jitpack.io/kmurerwa/murerwa-downloader)

## Supported Android Versions
Murerwa downloader supports all Android versions since `Android 5.0` and `API Level 21`.

## Installing
To install Murerwa Downloader, make sure you have enabled jitpack depositories. Make sure the following dependencies are in your root (project-level) `build.gradle` file or on your `settings.gradle` file if you are using Android Studio 2020.3.1 (Arctic Fox) and above.
    
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }

In your app-level `build.gradle` file, add the following dependency. Check the top of this file for the latest version.

    dependencies {
        implementation 'com.github.kmurerwa:murerwa-downloader:Version'
    }

## Usage
To use Murerwa Downloader in a Fragment or Activity, implement the `DownloadInterface` in your Fragment/Activity

    class MyFragment : Fragment, DownloadInterface {
        ...
    }

To initiate a download, create an instance of the `FileDownloader` class in the Fragment/Activity you want to perform downloads.

    val fileDownloader = FileDownloader(
        downloadLink = url,
        context = requireActivity(),
        fileName = "test.jpeg",
        downloadInterface = this
    )

Start the download, by simply calling the download function of the class.

    fileDownloader.downloadFile()

To observe the download progress of the library, implement to `DownloadInterface` class in your Fragment.

    class MyFragment : Fragment, DownloadInterface {
        ...
    }


## User Feedback
The `DownloadInterface` of the Murerwa Downloader library has four functions you can override as shown below.

### 1. OnDownloadProgressChanged
To keep track of download progress changes, implement the `onDownloadProgressChanged` function and perform any UI/UX tasks

    override fun onDownloadProgressChanged(newProgress: Int) {
        // Maybe show the user the download progress
    }

### 2. OnErrorOccurred
To display error messages if the download fails, override the `onErrorOccurred` function.

    override fun onErrorOccurred(error: String) {
        // Display an error message or log error
    }

### 3. OnDownloadStarted
The library issues a callback when download starts. To show a message, simply override the `onDownloadStarted` function

    override fun onDownloadStarted() {
        // Do something
    }

### 4. OnDownloadCompleted
The library also issues a callback when download completes. To show a message, simply override the `onDownloadCompleted` function

    override fun onDownloadCompleted() {
        // Do something
    }

If you do not override any of these functions, a default message is logged to your console.

## Contribution
If your would like to contribute to the project, you could complete any of the following tasks;
1. Allow ability to request storage permissions from inside the library
