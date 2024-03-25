package com.example.snapbin.model

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.snapbin.Navigation.Routes
import com.example.snapbin.R
import com.example.snapbin.model.data.Snap
import com.example.snapbin.model.data.SnapStatus
import com.example.snapbin.model.data.Urgency
import com.example.snapbin.screens.Event
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SnapScreenViewModel: ViewModel() {
    var snapUrl = mutableStateOf(Uri.EMPTY)
    var snapId = ""
    var isNew = false
    var location = mutableStateOf(GeoPoint(0.0,0.0))
    var description = mutableStateOf("")
    var aboutevent = mutableStateOf("")
    var urgency = mutableStateOf(Urgency.NOT_URGENT)
    var error : MutableState<Int?> = mutableStateOf(null)
    var inProgress = mutableStateOf(false)
    lateinit var navController: NavController

    fun doSnapFunction(){
        inProgress.value = true
        if(isNew){
            val filename = "/snapImages/${Firebase.auth.uid}/${LocalDateTime.now()}.jpg"
            Firebase.storage.getReference(filename).putFile(snapUrl.value).addOnSuccessListener {
                val snap = Snap("",location.value, Firebase.auth.uid!!,filename,"",
                    java.util.Date(), description.value,urgency.value,urgency.value, SnapStatus.PENDING)
                snap.submitSnap().addOnSuccessListener {
                    inProgress.value = false
                    navController.popBackStack(Routes.HOME_SCREEN,false)
                }.addOnFailureListener {
                    inProgress.value = false
                    error.value = R.string.error_snap_creation
                }
            }.addOnFailureListener{
                inProgress.value = false
                error.value = R.string.error_snap_image
            }
        }
        else{
            Firebase.firestore.collection("/snaps/").document(snapId).delete().addOnSuccessListener {
                inProgress.value = false
                navController.popBackStack(Routes.HOME_SCREEN,false)
            }.addOnFailureListener {
                inProgress.value = false
                error.value = R.string.error_snap_deletion
            }
        }
    }

    private val firestore = FirebaseFirestore.getInstance()
    private val eventsCollection = firestore.collection("events")

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> get() = _events

    fun fetchEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            eventsCollection.get()
                .addOnSuccessListener { documents ->
                    val eventList = mutableListOf<Event>()
                    for (document in documents) {
                        val event = document.toObject(Event::class.java)
                        eventList.add(event)
                    }
                    _events.value = eventList
                }
                .addOnFailureListener { e ->
                    // Handle failure
                    println("Error fetching events: $e")
                }
        }
    }
}