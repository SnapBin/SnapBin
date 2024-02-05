package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapbin.Components.*
import com.example.snapbin.Navigation.Screen
import com.example.snapbin.Navigation.SnapBinAppRoute
import com.example.snapbin.Navigation.SystemBackButtonHandler
import com.example.snapbin.R
import com.example.snapbin.data.LoginViewModel
import com.example.snapbin.data.UIEvent

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)


    ){
        Column(modifier = Modifier.fillMaxSize()) {
            NormalTextComponents(value = stringResource(R.string.Title))
            WelcomeComponent(value = "Welcome Back")

            MyTextFieldComponent(labelValue = stringResource(R.string.Email), imageVector = Icons.Default.Mail,
                onTextSelected = {
                loginViewModel.onEvent(UIEvent.EmailChanged(it))
            })

            PasswordFieldComponent(labelValue = "Password", imageVector = Icons.Default.Lock,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.PasswordChanged(it))

                })
            Spacer(modifier = Modifier.height(10.dp))
            NormalTextComponent(value = stringResource(R.string.Forgot_your_Passsword))
            Spacer(modifier = Modifier.height(10.dp))
            ButtonComponent(value = "Login",
                onButtonClicked = {
                    loginViewModel.onEvent(UIEvent.RegisterButtonClicked)
                })
//            Spacer(modifier = Modifier.height(40.dp))
            DividerTextComponent()

            ClicableLoginTextComponents(tryingToLogin = false, onTextSelected = {
                SnapBinAppRoute.navigateTo(Screen.SignUpScreen)

            })
        }

    }

    SystemBackButtonHandler {
        SnapBinAppRoute.navigateTo(Screen.SignUpScreen)
    }
}

@Preview
@Composable
fun LoginScreenPreview()
{
    LoginScreen()
}