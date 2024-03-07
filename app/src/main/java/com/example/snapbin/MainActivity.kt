package com.example.snapbin

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.snapbin.app.SnapBinapp
import com.example.snapbin.model.DeviceInfo
import com.example.snapbin.model.RootNavViewModel
import com.example.snapbin.ui.theme.SnapBinTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import org.osmdroid.config.Configuration


class MainActivity : ComponentActivity() {
    private val missingPermissions = mutableListOf<String>()
    private var wasLoggedIn = false
    private val firebaseAuthListener: FirebaseAuth.AuthStateListener =
        FirebaseAuth.AuthStateListener {
            if (wasLoggedIn && it.currentUser == null) {
                recreate()
            } else if (it.currentUser != null) {
                wasLoggedIn = true
            }
        }
    fun requestMissingPermissions(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(missingPermissions.size > 0) ActivityCompat.requestPermissions(this,missingPermissions.toTypedArray(),2)
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            val providerEnabled = if(Build.VERSION.SDK_INT > 30)
                locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER)
            else
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootNavViewModel = RootNavViewModel()
        rootNavViewModel.isLoggedIn.value = Firebase.auth.uid != null
        //Init firebase
        Firebase.auth.addIdTokenListener( FirebaseAuth.IdTokenListener{
            rootNavViewModel.isLoggedIn.value = Firebase.auth.uid != null
            if(Firebase.auth.uid != null){
                rootNavViewModel.email.value = Firebase.auth.currentUser!!.email ?: ""
                rootNavViewModel.displayName.value = Firebase.auth.currentUser!!.displayName ?: ""
            }
            else{
                rootNavViewModel.email.value = ""
                rootNavViewModel.displayName.value = ""
            }

        })
        Firebase.auth.addAuthStateListener(firebaseAuthListener)
        Firebase.firestore.firestoreSettings = firestoreSettings { isPersistenceEnabled = false }
        if(DeviceInfo.isEmulator){
            try {
                Firebase.auth.useEmulator("10.0.2.2", 9099)
                Firebase.firestore.useEmulator("10.0.2.2", 8080)
                Firebase.functions.useEmulator("10.0.2.2", 5001)
            }
            catch(_: Exception){

            }

        }
        //Init OSMDroid
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        setContent {
            SnapBinTheme {
                requestLocationPermission()
                requestCameraPermission()
                requestMissingPermissions()
                SnapBinapp()
            }
        }
    }

    fun requestCameraPermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            missingPermissions.add(android.Manifest.permission.CAMERA)
        }
    }
    fun requestLocationPermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            missingPermissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

}




//@Preview
//@Composable
//fun `DefaultView-ofSignupScreen`() {
//    SnapBinapp()
//}