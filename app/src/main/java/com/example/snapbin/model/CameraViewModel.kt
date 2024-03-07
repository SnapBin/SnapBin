package com.example.snapbin.model

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CameraViewModel: ViewModel(){
    var photoUri = mutableStateOf(Uri.EMPTY)
    var takeImage = mutableStateOf(true)
    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        photoUri.value = uri
        takeImage.value = false
    }
}