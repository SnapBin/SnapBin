package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.ButtonComponent
import com.example.snapbin.Components.NormalTextComponents
import com.example.snapbin.R
import com.example.snapbin.data.SignUpViewModel

@Composable
fun HomeScreen(signUpViewModel: SignUpViewModel = viewModel()){

    Scaffold(
        topBar = {
            AppToolbar(toolbarTitle = stringResource(id = R.string.app_name),
                logoutButtonClicked = {
                    signUpViewModel.logout()

            })
        }
    ){
        paddingValues ->

        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ){
            Column(modifier = Modifier.fillMaxSize()) {

            }
        }



    }

}

