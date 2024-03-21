package com.example.snapbin.model

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.example.snapbin.model.data.Snap
import com.example.snapbin.model.data.SnapStatus
import com.google.firebase.firestore.ListenerRegistration
import org.osmdroid.util.GeoPoint

class MainNavViewModel : ViewModel(){
    lateinit var snapListListener: ListenerRegistration
    var currentLocation = mutableStateOf<GeoPoint>(GeoPoint(53.350140,-6.266155))
    var snapList = mutableStateListOf<Snap>()
    var locationEnabled = mutableStateOf(false)
    var cameraEnabled = mutableStateOf(false)
    var currentFloatingActionButton: MutableState<@Composable () -> Unit> = mutableStateOf({})
    // Temporary calculation locally
    val points: Int get() {
        return snapList.filter { it.status != SnapStatus.PENDING }.fold(0) { i, s ->
            i + (s.status.value + 1)*10
        }
    }
    init{
//        snapListListener = Firebase.firestore.collection("/snaps").where(
//            Filter.equalTo("user",Firebase.auth.currentUser?.uid)).addSnapshotListener { snaps, ex ->
//            run {
//                if (snaps != null) {
//                    snapList.clear()
//                    snapList.addAll(snaps.toObjects(Snap::class.java))
//                }
//            }
//        }
    }
    fun checkPermissions(context: Context){
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providerEnabled = if(Build.VERSION.SDK_INT > 30)
            locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER)
        else
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        cameraEnabled.value = ActivityCompat.checkSelfPermission(
            context,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        locationEnabled.value = providerEnabled
    }
    override fun onCleared(){
        snapListListener.remove()
        snapList.clear()
    }
}