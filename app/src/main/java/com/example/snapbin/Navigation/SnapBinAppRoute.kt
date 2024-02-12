package com.example.snapbin.Navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {

    object SignUpScreen : Screen()
    object TermsandConditionsScreen : Screen()
    object LoginScreen: Screen()
    object HomeScreen: Screen()


}


object SnapBinAppRoute {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignUpScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }

}