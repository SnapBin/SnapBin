package com.example.snapbin.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Components.AppToolbar
import com.example.snapbin.Components.ContactComponent
import com.example.snapbin.Components.EquipementComponent
import com.example.snapbin.Components.LocationComponent
import com.example.snapbin.Components.NavigationDrawerBody
import com.example.snapbin.Components.NavigationDrawerHeader
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.data.home.HomeViewModel
import com.example.snapbin.model.SnapScreenViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
class CreateEventViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val eventsCollection = firestore.collection("events")

    fun createEvent(event: Event) {
        // Add the event to Firestore
        eventsCollection.add(event)
            .addOnSuccessListener { documentReference ->
                // Handle success
                // You can log the document ID if needed
                println("Event created with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                // Handle failure
                println("Error creating event: $e")
            }
    }
}
data class Event(
    val about: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val equipmentProvided: String,
    val organisedBy: String,
    val locationaddress: String
)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreateEventScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    isNew: Boolean = true,
    vm: SnapScreenViewModel = viewModel(),
    createEventViewModel: CreateEventViewModel = viewModel()
) {
    var locationText by remember { mutableStateOf("") } // Mutable state variable to store location text
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var startDateInput by remember { mutableStateOf("") }
    var endDateInput by remember { mutableStateOf("") }
    var equipmentProvidedText by remember { mutableStateOf("") }
    var organisedby by remember { mutableStateOf("") }
    var locationaddress by remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    homeViewModel.getUserData()

    // Permission request launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // If permission granted, get the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    // Handle location result
                    location?.let {
                        // Update locationText with live coordinates
                        locationText = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure to get location
                    locationText = "Unable to retrieve location: ${e.message}"
                }
        } else {
            // Handle case when permission is not granted
            locationText = "Location permission denied."
        }
    }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xA7D0A1).copy(alpha = 0.6f))
        ){
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
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Event Name",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        Modifier
                            .height((configuration.screenHeightDp * 0.10).dp)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            .background(color = Color(0xA7D0A1).copy(alpha = 0.6f))
                    ) {
                        OutlinedTextField(
                            value = vm.aboutevent.value,
                            enabled = isNew,
                            onValueChange = { vm.aboutevent.value = it },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
//                    Spacer(modifier = Modifier.height(8.dp))
                    // Button to set location on map and display live coordinates
                    Button(
                        onClick = {
                            // Request location permission if not granted
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                // If permission granted, get the last known location
                                fusedLocationClient.lastLocation
                                    .addOnSuccessListener { location ->
                                        // Handle location result
                                        location?.let {
                                            // Update locationText with live coordinates
                                            locationText = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        // Handle failure to get location
                                        locationText = "Unable to retrieve location: ${e.message}"
                                    }
                            } else {
                                // Request location permission if not granted
                                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Text(text = "Live Location")
                    }
                    // Display live location coordinates
                    Text(text = locationText)


//                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Meeting Point/Cleaning Location",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    LocationComponent(labelValue = "Enter the address", onTextSelected = {locationaddress = it} )
//                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            .background(color = Color(0xA7D0A1).copy(alpha = 0.6f))
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Meeting Date/Time",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(end = 10.dp)
                            )

                            // Start Date/Time TextField
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text(
                                    text = "Start Date/Time: ",
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                TextField(
                                    value = startDateInput,
                                    onValueChange = { startDateInput = it },
                                    placeholder = { Text("DD/MM/YYYY HH:MM") },
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            // End Date/Time TextField
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 10.dp)
                            ) {
                                Text(
                                    text = "End Date/Time: ",
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                TextField(
                                    value = endDateInput,
                                    onValueChange = { endDateInput = it },
                                    placeholder = { Text("DD/MM/YYYY HH:MM") },
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

//                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Equipment Provided",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    EquipementComponent(labelValue = "Equipment Provided", onTextSelected = {equipmentProvidedText = it})

//                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Organised Community",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    ContactComponent(labelValue = "Community Contact", onTextSelected = {organisedby = it})

                    Button(
                        onClick = {
                            // Create Event button clicked
                            val event = Event(
                                about = vm.aboutevent.value,
                                location = locationText,
                                startDate = startDateInput,
                                endDate = endDateInput,
                                equipmentProvided = equipmentProvidedText,
                                organisedBy = organisedby,
                                locationaddress = locationaddress
                            )
                            createEventViewModel.createEvent(event)
                            // Navigate to checkEvent screen
                            navController.navigate(Routes.HOME_SCREEN)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(text = "Create Event")
                    }



                }
            }

        }

    }
}
