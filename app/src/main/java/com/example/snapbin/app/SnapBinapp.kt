package com.example.snapbin.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.snapbin.Navigation.SnapBinNavigationGraph
import com.example.snapbin.data.home.HomeViewModel
import com.example.snapbin.model.RootNavViewModel

@Composable
fun SnapBinapp(homeViewModel: HomeViewModel = viewModel())
{
    val rootNavViewModel = RootNavViewModel()
    homeViewModel.checkForActiveSession()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
//        if(homeViewModel.isUserLoggedIn.value ==true){
//            navController.navigate(Routes.HOME_SCREEN)
//        }
        SnapBinNavigationGraph(rememberNavController(), rootNavViewModel)
    }
}
