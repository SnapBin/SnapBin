package com.example.snapbin.widgets

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import com.example.snapbin.R

fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}
@DrawableRes
fun getCurrentLocaleIcon(activity: Activity): Int{
    val locale = ConfigurationCompat.getLocales(activity.resources.configuration).get(0)
    return when(locale?.language){
        "hu" -> R.drawable.hu
        "de" -> R.drawable.de
        "fr" -> R.drawable.fr
        "fi" -> R.drawable.fi
        "it" -> R.drawable.it
        else -> R.drawable.gb
    }
}