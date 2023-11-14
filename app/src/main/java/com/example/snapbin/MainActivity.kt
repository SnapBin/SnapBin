package com.example.snapbin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import com.example.snapbin.app.SnapBinapp
import com.example.snapbin.screens.SignUpScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnapBinapp()
        }
    }
}


@Preview
@Composable
fun `DefaultView-ofSignupScreen`() {
    SnapBinapp()
}

