// HomeScreen.kt
package com.example.snapbin.screens

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.NavigationDrawerBody
import com.example.snapbin.Components.NavigationDrawerHeader
import com.example.snapbin.MainActivity
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.home.HomeViewModel
import com.example.snapbin.helper.ConnectionState
import com.example.snapbin.helper.MapHelper
import com.example.snapbin.helper.connectivityState
import com.example.snapbin.model.MainNavViewModel
import com.example.snapbin.view.map.MapView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(), mainNavViewModel: MainNavViewModel = viewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    val locationEnabled = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    homeViewModel.getUserData()
    val connectivity = connectivityState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(toolbarTitle = stringResource(id = R.string.app_name),
                logoutButtonClicked = {
                    homeViewModel.logout(navController)
                },
                navigationIconClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            NavigationDrawerHeader(homeViewModel.emailId.value)
            NavigationDrawerBody(navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = {navigationItem ->
                    when (navigationItem.itemId) {
                        "homeScreen" -> navController.navigate(Routes.HOME_SCREEN)
                        "volunteerScreen" -> navController.navigate(Routes.VOLUNTEER_SCREEN)
                        "reportScreen" -> navController.navigate(Routes.REPORT_SCREEN)
                        "accountScreen" -> navController.navigate(Routes.ACCOUNT_SCREEN)
                        "mapScreen" -> navController.navigate(Routes.MAP_SCREEN)
                        "listScreen" -> navController.navigate(Routes.ListSnapScreen)
                    }
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }

                })
        }

    ){paddingValues ->

        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ){

            Column(
                modifier = Modifier.padding(20.dp),
                )
            {
                Box(
                    Modifier
                        .height((configuration.screenHeightDp * 0.6).dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .aspectRatio(1.0f) // fixed aspect ratio could be usefull to resize the map
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        .background(color = Color.White)
                ) {
                    MapView(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        it.controller.setZoom(14.0)
                        it.controller.setCenter(mainNavViewModel.currentLocation.value)
                        MapHelper.addSnapsToMap(
                            context,
                            navController,
                            configuration,
                            it,
                            mainNavViewModel.snapList
                        )
                        it.setOnTouchListener { v, e ->
                            run {
                                if (connectivity.value == ConnectionState.Available) {
                                    if (navController.currentBackStackEntry?.destination?.route != Routes.MAP_SCREEN)
                                        navController.navigate(Routes.MAP_SCREEN)
                                }
                                true
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                if (connectivity.value == ConnectionState.Available) {
                    if (cameraPermission.status.isGranted && locationEnabled.status.isGranted && mainNavViewModel.locationEnabled.value) {
                        Button(
                            onClick = {
                                navController.navigate(Routes.CAMERA_SCREEN)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)

                        ) {
                            Text(
                                text = "Take A snap",
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    } else {
                        Button(
                            {
                                (context as MainActivity).requestMissingPermissions()
                                if(locationEnabled.status.isGranted && cameraPermission.status.isGranted){
                                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    context.startActivity(intent)
                                }
                                mainNavViewModel.checkPermissions(context)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                if(!locationEnabled.status.isGranted ||
                                    !cameraPermission.status.isGranted)
                                    "Button Request permission"
                                else "Turn on Location",
                                fontSize = 24.sp,
                                lineHeight = 27.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(5.dp)
                            )
                        }
                    }

                }
            }

        }

    }

}