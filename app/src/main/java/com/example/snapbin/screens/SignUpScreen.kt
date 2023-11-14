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
import com.example.snapbin.Components.*
import com.example.snapbin.Navigation.Screen
import com.example.snapbin.Navigation.SnapBinAppRoute
import com.example.snapbin.R

@Composable
fun SignUpScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

        
    ) {
        Column (modifier = Modifier.fillMaxSize()){
            NormalTextComponents(value = stringResource(R.string.Title))
            WelcomeComponent(value = stringResource(R.string.Welcome))
//            Spacer(modifier = Modifier.height(10.dp))
            MyTextFieldComponent(labelValue = stringResource(R.string.FirstName),
                imageVector = Icons.Default.Person
            )
            MyTextFieldComponent(labelValue = stringResource(R.string.Lastname), imageVector = Icons.Default.Person)

            MyTextFieldComponent(labelValue = stringResource(R.string.Email), imageVector = Icons.Default.Mail)

            PasswordFieldComponent(labelValue = stringResource(R.string.Password), imageVector = Icons.Default.Lock)

            PasswordFieldComponent(labelValue = stringResource(R.string.Re_confirm), imageVector = Icons.Default.Lock)
            
            CheckboxComponents(value = stringResource(R.string.Terms_and_condition), onTextSelected = {
                SnapBinAppRoute.navigateTo(Screen.TermsandConditionsScreen)

            })

            ButtonComponent(value = stringResource(R.string.Sign_in))


        }

    }
}

@Preview
@Composable
fun DefaultViewofSignupScreen() {
    SignUpScreen()
}
