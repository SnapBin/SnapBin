package com.example.snapbin.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.data.NavigationDrawer
import com.example.snapbin.data.home.HomeViewModel

//import androidx.navigation.NavController

@Composable
fun ReportScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    NavigationDrawer(navController = navController, homeViewModel = homeViewModel)
}
