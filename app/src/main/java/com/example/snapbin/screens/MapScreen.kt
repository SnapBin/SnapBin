package com.example.snapbin.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapbin.helper.MapHelper
import com.example.snapbin.model.MainNavViewModel
import com.example.snapbin.view.map.MapView


@Composable
fun MapScreen(navController: NavController,mainNavViewModel: MainNavViewModel = viewModel()){
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val nightMode = isSystemInDarkTheme()
    var centerSet by remember { mutableStateOf(false) }
    MapView(
        Modifier.fillMaxSize()
    ) {
        it.controller.setZoom(14.6)
        it.minZoomLevel = 5.6
        it.maxZoomLevel = 20.0
        it.setMultiTouchControls(true)
        it.setMultiTouchControls(true)
        if(!centerSet){
            it.controller.setCenter(mainNavViewModel.currentLocation.value)
            centerSet = true
        }
        MapHelper.addSnapsToMap(context,navController,configuration,it,mainNavViewModel.snapList)
    }
}