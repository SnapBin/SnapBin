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
import com.example.snapbin.model.data.Snap
import com.example.snapbin.screens.AccountScreen
import com.example.snapbin.screens.CameraScreen
import com.example.snapbin.screens.CheckEventScreen
import com.example.snapbin.screens.CreateEventScreen
import com.example.snapbin.screens.HomeScreen
import com.example.snapbin.screens.ListSnapScreen
import com.example.snapbin.screens.LoginScreen
import com.example.snapbin.screens.MapScreen
import com.example.snapbin.screens.OpenSnapScreen
import com.example.snapbin.screens.ReportScreen
import com.example.snapbin.screens.SignUpScreen
import com.example.snapbin.screens.SnapScreenInfo
import com.example.snapbin.screens.TermsandConditionsScreen
import com.example.snapbin.view.scaffolds.MainScaffold
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date

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

                MapScreen(navController,mainNavViewModel)
            }
            composable(Routes.CAMERA_SCREEN){

                CameraScreen(navController,mainNavViewModel.currentLocation.value)
            }
            composable(Routes.SINGLE_SNAP){
                val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).addLast(
                    KotlinJsonAdapterFactory()
                ).build()
                val adapter = moshi.adapter(Snap::class.java)
                val snap = adapter.fromJson(it.arguments?.getString("snap")!!)
                OpenSnapScreen(snap!!,navController,mainNavViewModel, snap.id.isEmpty())
            }
            composable(Routes.CREATE_EVENT){
                CreateEventScreen(navController)
            }
            composable(Routes.CHECK_EVENT){
                CheckEventScreen(navController)
            }
            composable(Routes.ListSnapScreen){
                ListSnapScreen(mainNavViewModel.snapList,navController,false)
            }

            composable(Routes.SnapScreenInfo){
                SnapScreenInfo(navController)
            }
        }

    }




}



