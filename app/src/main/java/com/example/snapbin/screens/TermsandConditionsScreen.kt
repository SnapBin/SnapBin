package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snapbin.Components.WelcomeComponent
import com.example.snapbin.Navigation.Screen
import com.example.snapbin.Navigation.SnapBinAppRoute
//import com.example.snapbin.Navigation.SystemBackButtonHandler
import com.example.snapbin.R

@Composable
fun TermsandConditionsScreen(navController: NavHostController) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)) {
        WelcomeComponent(value = stringResource(R.string.Terms_and_conditions_heading))

    }
//    SystemBackButtonHandler {
//        SnapBinAppRoute.navigateTo(Screen.SignUpScreen)
//    }
}
