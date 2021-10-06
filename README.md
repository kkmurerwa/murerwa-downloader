# Murerwa Downloader
Murerwa downloader is a custom downloader beside the default Android downloader that helps users download files on the fly and save them to folders that the default Android downloader cannot.

## Supported versions
Murerwa downloader supports all Android versions since `Android 5.0` and `API Level 21`.

## Installing
To install Murerwa Downloader, make sure you have enabled jitpack depositories. Make sure the following dependencies are in your root(project-level) `build.gradle` file
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }

In your app-level `build.gradle` file, add the following dependency
    dependencies {
        implementation 'com.github.xwaxes01:murerwa-downloader:1.0.0'
    }

## Contribution
If your would like to contribute to the project, you could complete any of the following tasks;
1. Allow ability to request storage permissions from inside the library