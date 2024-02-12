package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapbin.Components.ButtonComponent
import com.example.snapbin.Components.NormalTextComponents
import com.example.snapbin.R
import com.example.snapbin.data.SignUpViewModel

@Composable
fun HomeScreen(signUpViewModel: SignUpViewModel = viewModel()){
//    Surface(
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//
//
//    ){
//        Column(modifier = Modifier.fillMaxSize()) {
//            NormalTextComponents(value = stringResource(R.string.home))
//
//            ButtonComponent(value = stringResource(R.string.logout), onButtonClicked = {
//                signUpViewModel.logout()
//            },
//                isEnabled = true
//            )
//        }
//    }
    Scaffold(

    ){


    }

}

