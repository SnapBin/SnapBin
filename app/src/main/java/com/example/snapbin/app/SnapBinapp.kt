package com.example.snapbin.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapbin.Navigation.Screen
import com.example.snapbin.Navigation.SnapBinAppRoute
import com.example.snapbin.data.home.HomeViewModel
import com.example.snapbin.screens.HomeScreen
import com.example.snapbin.screens.LoginScreen
import com.example.snapbin.screens.SignUpScreen
import com.example.snapbin.screens.TermsandConditionsScreen

@Composable
fun SnapBinapp(homeViewModel: HomeViewModel = viewModel())
{
    homeViewModel.checkForActiveSession()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        if(homeViewModel.isUserLoggedIn.value ==true){
            SnapBinAppRoute.navigateTo(Screen.HomeScreen)
        }
        Crossfade(targetState = SnapBinAppRoute.currentScreen, label = "") { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.TermsandConditionsScreen -> {
                    TermsandConditionsScreen()
                }
                is Screen.LoginScreen -> {
                    LoginScreen()
                }
                is Screen.HomeScreen -> {
                    HomeScreen()
                }
            }

        }
    }
}
