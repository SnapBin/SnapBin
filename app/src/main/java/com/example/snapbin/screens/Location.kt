package com.example.snapbin.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.snapbin.app.SnapBinapp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class Location: ComponentActivity() {
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequired: Boolean =false

    override fun onResume(){
        super.onResume()
        if(locationRequired){
            startLocationUpdates()
        }
    }

    @SuppressLint("MissingPermissions")
    private fun startLocationUpdates() {
        locationCallback.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallback.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapsInitializer.initialize(this, MapsInitializer.Renderer.LATEST){

        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            var currentLocation by remember { mutableStateOf(LatLng(0.toDouble(),0.toDouble())) }

            val cameraPosition = rememberCameraPositionState{
                position = CameraPosition.fromLatLngZoom(
                    currentLocation,15f
                )
            }

            var cameraPositionState by remember { mutableStateOf(cameraPosition) }
            locationCallback = object :LocationCallback(){
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    for(location in p0.locations){
                        currentLocation = LatLng(location.latitude,location.longitude)

                        cameraPositionState = CameraPositionState(
                            position = CameraPosition.fromLatLngZoom(
                                currentLocation,15f
                            )

                        )
                    }
                }
            }
            LocationScreen(this@Location, currentLocation, cameraPositionState)

        }

    }

    @Composable
    fun LocationScreen(context : Context, currentLocation: LatLng, camerapsotionState: CameraPositionState) {
        val launcherMultiplePermission = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
                permissionMaps ->
            val areGranted = permissionMaps.values.reduce{acc,next -> acc && next}
            if (areGranted) {
                locationRequired = true
                startLocationUpdates()
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = camerapsotionState
            ) {
                Marker(
                    state = MarkerState(
                        position = currentLocation,
                    ),
                    title = "You",
                    snippet = "You're here!!!"
                )

            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Your location: ${currentLocation.latitude}/${currentLocation.longitude}")
                Button(onClick = {
                    if (permissions.all {
                            ContextCompat.checkSelfPermission(context,it) == PackageManager.PERMISSION_GRANTED
                        })
                    {
                        startLocationUpdates()
                    }
                    else
                    {
                        launcherMultiplePermission.launch(permissions)
                    }

                }) {
                    Text(text = "Get Your Location")
                }
            }
        }

    }
}