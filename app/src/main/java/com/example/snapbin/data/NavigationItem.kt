package com.example.snapbin.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val description: String,
    val itemId: String,
    val icon: ImageVector
)
