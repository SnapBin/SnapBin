package com.example.snapbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.snapbin.model.MainNavViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SavedReports(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(), mainNavViewModel: MainNavViewModel = viewModel()) {
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
                    ArchreportScreen()
                }
            }
        }
    }
}

data class Archreports(
    val userId: String,
    val location: String,
//    val datetime: java.util.Date,
    val description: String,
    val urgency: String,
    val sizeOfTrash: String,
    val typeOfTrash: String,
    val reportBy: String)


@Composable
fun ArchreportScreen() {
    val snapinfo by fetchArchReport()
    var selectedCategory by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var selectedEvent by remember {
            mutableStateOf<Archreports?>(null)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(snapinfo) { event -> // Use events.value instead of events
                ArchCard(archreports = event) { // Provide onClick lambda here
                    selectedEvent = event
                }
            }
        }
        if (selectedEvent != null) {
            Archdetaildialoug(
                archreports = selectedEvent,
                onDismiss = { selectedEvent = null }
            )
        }
    }
}
@Composable
fun Archdetaildialoug(archreports: Archreports?, onDismiss: () -> Unit) {
    if (archreports != null) {
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
//                    Text(text = "dateTime: ${archreports.datetime}", color = Color.Black)
                    Text(text = "Description: ${archreports.description}", color = Color.Black)
                    Text(text = "Urgency : ${archreports.urgency}", color = Color.Black)
                    Text(text = "sizeOfTrash: ${archreports.sizeOfTrash}", color = Color.Black)
                    Text(text = "Type of Trash: ${archreports.typeOfTrash}", color = Color.Black)
                    Text(text = "reportBy : ${archreports.reportBy}", color = Color.Black)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
@Composable
fun ArchCard(archreports: Archreports, onClick: () -> Unit) {
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
                text = archreports.location,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black
            )
//            Text(text = "dateTime: ${archreports.datetime}", color = Color.Black)
            Text(text = "Description: ${archreports.description}", color = Color.Black)
            Text(text = "Urgency : ${archreports.urgency}", color = Color.Black)
            Text(text = "sizeOfTrash: ${archreports.sizeOfTrash}", color = Color.Black)
            Text(text = "Type of Trash: ${archreports.typeOfTrash}", color = Color.Black)
            Text(text = "reportBy : ${archreports.reportBy}", color = Color.Black)
        }
    }
}
@Composable
fun fetchArchReport(): State<List<Archreports>> {
    val offersState = remember { mutableStateOf(emptyList<Archreports>()) }
    //val firestore = FirebaseFirestore.getInstance()
    //val collectionRef = firestore.collection("snapInfo")

    val db = Firebase.firestore

    LaunchedEffect(true) {
        //var query = collectionRef
        val query = db.collection("archievedata")

        val snapshot = query.get().await()
        val offersList = snapshot.documents.mapNotNull { document ->
            val id = document.id
            val userId = document.getString("userId") ?:""
            val location = document.getString("location") ?: ""
            val datetime = (document.get("datetime")) ?: ""
            val description = document.getString("description") ?: ""
            val urgency = document.getString("urgency") ?: ""
            val sizeOfTrash = document.getString("sizeOfTrash") ?: ""
            val typeOfTrash = document.getString("typeOfTrash") ?: ""
            val reportBy = document.getString("reportBy") ?: ""
            Archreports(
                userId = userId,
                location = location,
//                datetime = java.util.Date(),
                description = description,
                urgency = urgency,
                sizeOfTrash = sizeOfTrash,
                typeOfTrash = typeOfTrash,
                reportBy = reportBy)
        }
        offersState.value = offersList
    }
    return offersState
}