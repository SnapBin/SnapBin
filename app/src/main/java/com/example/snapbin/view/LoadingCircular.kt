package com.example.snapbin.view

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LoadingCircular(){
    CircularProgressIndicator(
        modifier = Modifier.size(80.dp),
        strokeWidth = 2.dp,
        color = MaterialTheme.colorScheme.primary
    )
}