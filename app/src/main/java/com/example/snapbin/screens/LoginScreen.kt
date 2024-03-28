package com.example.snapbin.screens

//import com.example.snapbin.Navigation.SystemBackButtonHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Components.ButtonComponent
import com.example.snapbin.Components.ClicableLoginTextComponents
import com.example.snapbin.Components.DividerTextComponent
import com.example.snapbin.Components.MyTextFieldComponent
import com.example.snapbin.Components.NormalTextComponent
import com.example.snapbin.Components.NormalTextComponents
import com.example.snapbin.Components.PasswordFieldComponent
import com.example.snapbin.Components.WelcomeComponent
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.login.LoginUIEvent
import com.example.snapbin.data.login.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)


        ){
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponents(value = stringResource(R.string.Title))
                WelcomeComponent(value = "Welcome")
                Spacer(modifier = Modifier.height(5.dp))
                // Adding the Box composable for centering the image
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // Image composable
                    Image(
                        painter = painterResource(id = R.drawable.logo), // Replace "your_image" with your image resource
                        contentDescription = "Your Image",
                        contentScale = ContentScale.Fit, // Adjust content scale as needed
                        modifier = Modifier
                            .size(85.dp)
                            .clip(shape = RoundedCornerShape(8.dp))

                    )
                }


                MyTextFieldComponent(labelValue = stringResource(R.string.Email), imageVector = Icons.Default.Mail,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.loginUIState.value.emailError
                )

                PasswordFieldComponent(labelValue = "Password", imageVector = Icons.Default.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))

                    },
                    errorStatus = loginViewModel.loginUIState.value.passwordError
                )
                Spacer(modifier = Modifier.height(10.dp))
                NormalTextComponent(value = stringResource(R.string.Forgot_your_Passsword))
                Spacer(modifier = Modifier.height(10.dp))
                ButtonComponent(value = "Login",
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                        navController.navigate(Routes.HOME_SCREEN)
                    },
                    isEnabled = loginViewModel.allValidationsPassed.value

                )
//            Spacer(modifier = Modifier.height(40.dp))
                DividerTextComponent()

                ClicableLoginTextComponents(tryingToLogin = false, onTextSelected = {
                    navController.navigate(Routes.SIGNUP_SCREEN)

                })
            }

        }

        if(loginViewModel.loginInProgress.value){
            CircularProgressIndicator()
        }

    }
//    SystemBackButtonHandler {
//        navController.navigate(Routes.SIGNUP_SCREEN)
//    }

}

