package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapbin.Components.*
import com.example.snapbin.Navigation.Screen
import com.example.snapbin.Navigation.SnapBinAppRoute
import com.example.snapbin.R
import com.example.snapbin.data.signup.SignUpViewModel
import com.example.snapbin.data.signup.SignUpUIEvent

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
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
                        signUpViewModel.onEvent(SignUpUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.firstNameError
                )
                MyTextFieldComponent(labelValue = stringResource(R.string.Lastname), imageVector = Icons.Default.Person,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.LastNameChanged(it))

                    },
                    errorStatus = signUpViewModel.registrationUIState.value.lastNameError
                )

                MyTextFieldComponent(labelValue = stringResource(R.string.Email), imageVector = Icons.Default.Mail,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(it))

                    },
                    errorStatus = signUpViewModel.registrationUIState.value.emailError
                )

                PasswordFieldComponent(labelValue = stringResource(R.string.Password), imageVector = Icons.Default.Lock,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(it))

                    },
                    errorStatus = signUpViewModel.registrationUIState.value.passwordError
                )

                ConfirmPasswordFieldComponent(labelValue = stringResource(R.string.Re_confirm), imageVector = Icons.Default.Lock,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.ConfirmPasswordChanged(it))

                    },
                    errorStatus = signUpViewModel.registrationUIState.value.confirmPasswordChangedError
                )

                CheckboxComponents(value = stringResource(R.string.Terms_and_condition),
                    onTextSelected = {
                        SnapBinAppRoute.navigateTo(Screen.TermsandConditionsScreen)

                    },
                    onCheckedChange = {
                        signUpViewModel.onEvent(SignUpUIEvent.PrivacyPolicyCheckBoxClicked(it))

                    }
                )

                ButtonComponent(value = stringResource(R.string.Sign_in),
                    onButtonClicked = {
                        signUpViewModel.onEvent(SignUpUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = signUpViewModel.allValidationsPassed.value
                )

                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))

                ClicableLoginTextComponents(tryingToLogin = true, onTextSelected = {
                    SnapBinAppRoute.navigateTo((Screen.LoginScreen))

                })

            }

        }
        if(signUpViewModel.signUpInProgress.value){
            CircularProgressIndicator()
        }



    }

}

