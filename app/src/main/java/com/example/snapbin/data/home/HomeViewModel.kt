// HomeViewModel.kt
package com.example.snapbin.data.home

import android.location.Location
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.data.NavigationItem
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    private val TAG = HomeViewModel::class.simpleName

    val navigationItemsList = listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = "homeScreen"
        ),
        NavigationItem(
            title = "Volunteer",
            icon = Icons.Default.VolunteerActivism,
            description = "Volunteer Screen",
            itemId = "volunteerScreen"
        ),
        NavigationItem(
            title = "Reports",
            icon = Icons.Default.Report,
            description = "Report Screen",
            itemId = "reportScreen"
        ),
        NavigationItem(
            title = "Account",
            icon = Icons.Default.AccountBox,
            description = "Account Screen",
            itemId = "accountScreen"
        ),
        NavigationItem(
            title = "Map",
            icon = Icons.Default.Map,
            description = "Map Screen",
            itemId = "mapScreen"
        ),
        NavigationItem(
            title = "List",
            icon = Icons.Default.FilterList,
            description = "List Screen",
            itemId = "listScreen"
        )

    )

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun logout(navController: NavHostController) {
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside sign out success")
                navController.navigate(Routes.LOGIN_SCREEN)
            } else {
                Log.d(TAG, "Inside sign out is not completed")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun checkForActiveSession() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d(TAG, "Valid session")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "User is not logged in")
            isUserLoggedIn.value = false
        }
    }

    val emailId: MutableLiveData<String> = MutableLiveData()

    fun getUserData() {
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also { email ->
                emailId.value = email
            }
        }
    }

    val currentLocation: MutableLiveData<Location> = MutableLiveData()

    fun updateCurrentLocation(location: Location) {
        currentLocation.value = location
    }
}
