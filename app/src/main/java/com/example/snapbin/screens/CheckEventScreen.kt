package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.NavigationDrawerBody
import com.example.snapbin.Components.NavigationDrawerHeader
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.home.HomeViewModel
import com.example.snapbin.model.SnapScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun CheckEventScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(),
                     snapScreenViewModel : SnapScreenViewModel = viewModel()) {
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
                    }
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        }
    ) { paddingValues ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EventList(events = snapScreenViewModel.events.value)

            }
        }
    }
}

@Composable
fun EventList(events: List<Event>) {
    Column {
        events.forEach { event ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                EventItem(event = event)
            }
        }
    }
}

@Composable
fun EventItem(event: Event) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Text(
            text = "Event Name: ${event.about}",
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Location: ${event.location}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Start Date/Time: ${event.startDate}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "End Date/Time: ${event.endDate}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Equipment Provided: ${event.equipmentProvided}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Organised By: ${event.organisedBy}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Location Address: ${event.locationaddress}")
    }
}
