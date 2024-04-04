package com.example.snapbin.widgets

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat

@Composable
fun LanguageSelector(){
    val langMenuExpanded = remember{ mutableStateOf(false)}
    val activity = (LocalContext.current as Activity)
    val selectableLanguages = mapOf(
        "en" to "English",
        "de" to "Deutsch",
        "fr" to "Français",
        "it" to "Italiano")
    Row() {
        FlagIconWithArrow(getCurrentLocaleIcon(activity), langMenuExpanded.value, onClick = {
            langMenuExpanded.value = !langMenuExpanded.value
        })
        DropdownMenu(
            expanded = langMenuExpanded.value,
            onDismissRequest = { langMenuExpanded.value = false }) {
            selectableLanguages.forEach {
                DropdownMenuItem(text = { Text(it.value) }, onClick = {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(it.key)
                    )
                })
            }
        }
    }
}
