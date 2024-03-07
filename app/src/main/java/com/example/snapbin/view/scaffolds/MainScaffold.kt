package com.example.snapbin.view.scaffolds

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationListenerCompat
import androidx.navigation.NavHostController
import com.example.snapbin.model.MainNavViewModel
import com.example.snapbin.model.RootNavViewModel
import org.osmdroid.util.GeoPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavHostController,rootVm: RootNavViewModel
) {
    val vm = MainNavViewModel()
    val context = LocalContext.current
    val locationProvider =
        if (Build.VERSION.SDK_INT > 30) LocationManager.FUSED_PROVIDER else LocationManager.GPS_PROVIDER
    vm.checkPermissions(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(locationProvider, 5000, 0.0f,
            object : LocationListenerCompat {
                override fun onLocationChanged(location: Location) {
                    vm.currentLocation.value = GeoPoint(location.latitude, location.longitude)
                }

                override fun onProviderDisabled(provider: String) {
                    if (provider == locationProvider) {
                        vm.locationEnabled.value = false
                    }
                }

                override fun onProviderEnabled(provider: String) {
                    if (provider == locationProvider) {
                        vm.locationEnabled.value = true
                    }
                }
            })
    }
    else{
        vm.locationEnabled.value = false
    }
}

