package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.NavigationDrawerBody
import com.example.snapbin.Components.NavigationDrawerHeader
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.home.HomeViewModel
import kotlinx.coroutines.launch

//import androidx.navigation.NavController

@Composable
fun ReportScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    homeViewModel.getUserData()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppToolbar(
                toolbarTitle = stringResource(id = R.string.app_name),
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
            NavigationDrawerBody(
                navigationDrawerItems = homeViewModel.navigationItemsList,
                onNavigationItemClicked = { navigationItem ->
                    when (navigationItem.itemId) {
                        "homeScreen" -> navController.navigate(Routes.HOME_SCREEN)
                        "volunteerScreen" -> navController.navigate(Routes.VOLUNTEER_SCREEN)
                        "reportScreen" -> navController.navigate(Routes.REPORT_SCREEN)
                        "accountScreen" -> navController.navigate(Routes.ACCOUNT_SCREEN)
                        "mapScreen" -> navController.navigate(Routes.MAP_SCREEN)
                    }
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = 80.dp), // Adjust this value for the height of the semi-circle
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                // Navigate to appropriate screen
                                navController.navigate(Routes.myreportscreen)
                            },
                            shape = CircleShape,
//                            colors = colorResource(id = R.color.ToppAppBarColor),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.ToppAppBarColor)),
                            modifier = Modifier
                                .padding(16.dp)
                                .size(150.dp)
                        ) {
                            Text(text = stringResource(R.string.my_reports))
                        }

                        Button(
                            onClick = {
                                // Navigate to appropriate screen
                                navController.navigate(Routes.savedreports)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.ToppAppBarColor)),
                            modifier = Modifier
                                .padding(16.dp)
                                .size(150.dp)
                        ) {
                            Text(text = stringResource(R.string.saved_reports))
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                // Navigate to appropriate screen
                                navController.navigate(Routes.deletedreports)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.ToppAppBarColor)),
                            modifier = Modifier
                                .padding(16.dp)
                                .size(150.dp)
                        ) {
                            Text(text = stringResource(R.string.deleted_reports))
                        }

                        Button(
                            onClick = {
                                // Navigate to appropriate screen
                                navController.navigate(Routes.nearbyreports)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.ToppAppBarColor)),
                            modifier = Modifier
                                .padding(16.dp)
                                .size(150.dp)
                        ) {
                            Text(text = stringResource(R.string.nearby_reports))
                        }
                    }
                }

            }
        }
    }

}
