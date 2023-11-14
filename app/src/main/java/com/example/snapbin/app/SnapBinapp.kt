package com.example.snapbin.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.snapbin.Navigation.Screen
import com.example.snapbin.Navigation.SnapBinAppRoute
import com.example.snapbin.screens.SignUpScreen
import com.example.snapbin.screens.TermsandConditionsScreen

@Composable
fun SnapBinapp()
{
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    )
    {
        Crossfade(targetState = SnapBinAppRoute.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.TermsandConditionsScreen -> {
                    TermsandConditionsScreen()
                }
            }

        }
    }
}
