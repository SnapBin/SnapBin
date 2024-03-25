package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Components.ButtonComponent
import com.example.snapbin.Components.CheckboxComponents
import com.example.snapbin.Components.ClicableLoginTextComponents
import com.example.snapbin.Components.ConfirmPasswordFieldComponent
import com.example.snapbin.Components.DividerTextComponent
import com.example.snapbin.Components.MyTextFieldComponent
import com.example.snapbin.Components.NormalTextComponents
import com.example.snapbin.Components.PasswordFieldComponent
import com.example.snapbin.Components.WelcomeComponent
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.signup.SignUpUIEvent
import com.example.snapbin.data.signup.SignUpViewModel

@Composable
fun SignUpScreen(navController: NavHostController, signUpViewModel : SignUpViewModel = viewModel()) {
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
                        navController.navigate(Routes.TERMS_AND_CONDITIONS_SCREEN)

                    },
                    onCheckedChange = {
                        signUpViewModel.onEvent(SignUpUIEvent.PrivacyPolicyCheckBoxClicked(it))

                    }
                )

                ButtonComponent(value = stringResource(R.string.Sign_in),
                    onButtonClicked = {
                        signUpViewModel.onEvent(SignUpUIEvent.RegisterButtonClicked)
                        navController.navigate(Routes.HOME_SCREEN)
                    },
                    isEnabled = signUpViewModel.allValidationsPassed.value
                )

                DividerTextComponent()
                Spacer(modifier = Modifier.height(20.dp))

                ClicableLoginTextComponents(tryingToLogin = true, onTextSelected = {
                    navController.navigate(Routes.LOGIN_SCREEN)

                })


            }

        }
        if(signUpViewModel.signUpInProgress.value){
            CircularProgressIndicator()
        }



    }

}

