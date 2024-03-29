package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.NavigationDrawerBody
import com.example.snapbin.Components.NavigationDrawerHeader
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.home.HomeViewModel
import com.example.snapbin.model.SnapScreenViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
                OfferListScreen()
            }
        }
    }
}


@Composable
fun OfferListScreen(category: String? = null) {
    val events by fetchEvent(category)
    var selectedCategory by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var selectedEvent by remember {
            mutableStateOf<Event?>(null)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(events) { event -> // Use events.value instead of events
                EventCard(event = event) { // Provide onClick lambda here
                    selectedEvent = event
                }
            }
        }
        if (selectedEvent != null) {
            OfferDetailsDialog(
                event = selectedEvent,
                onDismiss = { selectedEvent = null }
            )
        }
    }
}


@Composable
fun EventCard(event: Event, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        backgroundColor = colorResource(id = R.color.Bar_Color),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp) // Adjust the corner radius as needed
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = event.about,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )
            Text(text = "Location Address: ${event.locationaddress}", color = Color.Black)
            Text(text = "Your Location: ${event.location}", color = Color.Black)
            Text(text = "Start Date/Time : ${event.startDate}", color = Color.Black)
            Text(text = "End Date/Time: ${event.endDate}", color = Color.Black)
            Text(text = "Equipment provided: ${event.equipmentProvided}", color = Color.Black)
            Text(text = "Organiser : ${event.organisedBy}", color = Color.Black)
        }
    }
}

@Composable
fun fetchEvent(category: String? = null): State<List<Event>> {
    val offersState = remember { mutableStateOf(emptyList<Event>()) }
    val firestore = FirebaseFirestore.getInstance()
    val collectionRef = firestore.collection("events")

    LaunchedEffect(true) {
        var query = collectionRef
        if (category != null) {
            query = query.whereEqualTo("category", category) as CollectionReference
        }
        val snapshot = query.get().await()
        val offersList = snapshot.documents.mapNotNull { document ->
            val id = document.id
            val about = document.getString("about") ?:""
            val location = document.getString("location") ?: ""
            val startDate = document.getString("startDate") ?: ""
            val endDate = document.getString("endDate") ?: ""
            val equipmentProvided = document.getString("equipmentProvided") ?: ""
            val organisedBy = document.getString("organisedBy") ?: ""
            val locationaddress = document.getString("locationaddress") ?: ""
            Event(
                about = about,
                locationaddress = locationaddress,
                location = location,
                startDate = startDate,
                endDate = endDate,
                equipmentProvided = equipmentProvided,
                organisedBy = organisedBy)
        }
        offersState.value = offersList
    }

    return offersState
}
@Composable
fun OfferDetailsDialog(event: Event?, onDismiss: () -> Unit) {
    if (event != null) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = 8.dp,
                backgroundColor = colorResource(id = R.color.Menus),
                shape = RoundedCornerShape(16.dp) // Adjust the corner radius as needed
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "About: ${event.about}", color = Color.Black)
                    Text(text = "Address: ${event.locationaddress}", color = Color.Black)
                    Text(text = "Live Location: ${event.location}", color = Color.Black)
                    Text(text = "Start Date: ${event.startDate}", color = Color.Black)
                    Text(text = "End Date: ${event.endDate}", color = Color.Black)
                    Text(text = "Equipment: ${event.equipmentProvided}", color = Color.Black)
                    Text(text = "Organised By: ${event.organisedBy}", color = Color.Black)

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}




