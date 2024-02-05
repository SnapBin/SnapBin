package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.snapbin.R
import com.example.snapbin.data.LoginViewModel
import com.example.snapbin.data.UIEvent

@Composable
fun SignUpScreen(loginViewModel: LoginViewModel = viewModel()) {
    Surface(
            color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

        
    ) {
        Column (modifier = Modifier.fillMaxSize()){
            NormalTextComponents(value = stringResource(R.string.Title))
            WelcomeComponent(value = stringResource(R.string.Welcome))

            MyTextFieldComponent(labelValue = stringResource(R.string.FirstName),
                imageVector = Icons.Default.Person,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.FirstNameChanged(it))
                },
                errorStatus = loginViewModel.registrationUIState.value.firstNameError
            )
            MyTextFieldComponent(labelValue = stringResource(R.string.Lastname), imageVector = Icons.Default.Person,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.LastNameChanged(it))

                },
                errorStatus = loginViewModel.registrationUIState.value.lastNameError
            )

            MyTextFieldComponent(labelValue = stringResource(R.string.Email), imageVector = Icons.Default.Mail,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.EmailChanged(it))

                },
                errorStatus = loginViewModel.registrationUIState.value.emailError
            )

            PasswordFieldComponent(labelValue = stringResource(R.string.Password), imageVector = Icons.Default.Lock,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.PasswordChanged(it))

                },
                errorStatus = loginViewModel.registrationUIState.value.passwordError
            )

            PasswordFieldComponent(labelValue = stringResource(R.string.Re_confirm), imageVector = Icons.Default.Lock,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.PasswordChanged(it))

                },
                errorStatus = loginViewModel.registrationUIState.value.passwordError
            )
            
            CheckboxComponents(value = stringResource(R.string.Terms_and_condition), onTextSelected = {
                SnapBinAppRoute.navigateTo(Screen.TermsandConditionsScreen)

            })

            ButtonComponent(value = stringResource(R.string.Sign_in),
                onButtonClicked = {
                    loginViewModel.onEvent(UIEvent.RegisterButtonClicked)
                }
            )

            DividerTextComponent()
            Spacer(modifier = Modifier.height(20.dp))

            ClicableLoginTextComponents(tryingToLogin = true, onTextSelected = {
                SnapBinAppRoute.navigateTo((Screen.LoginScreen))

            })

        }

    }
}

@Preview
@Composable
fun DefaultViewofSignupScreen() {
    SignUpScreen()
}
