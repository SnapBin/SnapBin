package com.example.snapbin.Navigation

import VolunteerScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snapbin.model.MainNavViewModel
import com.example.snapbin.model.RootNavViewModel
import com.example.snapbin.screens.AccountScreen
import com.example.snapbin.screens.CameraScreen
import com.example.snapbin.screens.HomeScreen
import com.example.snapbin.screens.LoginScreen
import com.example.snapbin.screens.MapScreen
import com.example.snapbin.screens.ReportScreen
import com.example.snapbin.screens.SignUpScreen
import com.example.snapbin.screens.TermsandConditionsScreen
import com.example.snapbin.view.scaffolds.MainScaffold

@Composable
fun SnapBinNavigationGraph(navController: NavHostController, vm: RootNavViewModel = viewModel(), mainNavViewModel: MainNavViewModel = viewModel()){
    val navController = rememberNavController()
    if(vm.isLoggedIn.value){
        MainScaffold(navController,vm)
    }
    else{
        NavHost(navController = navController, startDestination = Routes.SIGNUP_SCREEN ){
            composable(Routes.SIGNUP_SCREEN){
                SignUpScreen(navController)
            }
            composable(Routes.LOGIN_SCREEN){
                LoginScreen(navController)
            }
            composable(Routes.HOME_SCREEN){
                HomeScreen(navController)
            }
            composable(Routes.VOLUNTEER_SCREEN){
                VolunteerScreen(navController)
            }
            composable(Routes.ACCOUNT_SCREEN){
                AccountScreen(navController)
            }
            composable(Routes.REPORT_SCREEN){
                ReportScreen(navController)
            }
            composable(Routes.TERMS_AND_CONDITIONS_SCREEN){
                TermsandConditionsScreen(navController)
            }
            composable(Routes.MAP_SCREEN){
                MapScreen(navController)
            }
            composable(Routes.CAMERA_SCREEN){
                CameraScreen(navController,mainNavViewModel.currentLocation.value)
            }

        }

    }




}



