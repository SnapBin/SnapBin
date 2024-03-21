package com.example.snapbin.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.snapbin.R
import com.example.snapbin.model.Date
import com.example.snapbin.model.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable

fun EditScreen(navController: NavController,profileViewModel: ProfileViewModel = viewModel()){
    val scroll= rememberScrollState()

    Scaffold(
        topBar = {TopAppBar(
            modifier = Modifier.background(Color.Blue),
            title = { Text(
                modifier = Modifier.padding(top=10.dp, start = 20.dp),
                text="Edit" ,
                fontSize = 30.sp ,
                fontWeight= FontWeight.Bold,
                color= Color.White)

            })},
        content = { paddingValues ->
            Log.d("Padding values", "$paddingValues")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll)
            ){
                EditBox(profileViewModel)
            }
        },
    )
}

@Composable
fun InfoEdit(profileViewModel: ProfileViewModel) {
    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("users")
    val documentRef = collectionRef.document(profileViewModel.userId)
    var userInfo by remember { mutableStateOf<MutableMap<String, Any>>(mutableMapOf()) }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        try {
            val documentSnapshot = documentRef.get().await()
            if (documentSnapshot != null) {
                val data = documentSnapshot.data
                if (data != null) {
                    userInfo = data.toMutableMap()
                    lastName = userInfo.getValue("lastName").toString()
                    firstName = userInfo.getValue("firstName").toString()
                    email = userInfo.getValue("email").toString()
                    birthDate = userInfo.getValue("birthDate").toString()
                    phoneNumber = userInfo.getValue("phoneNumber").toString()
                }
            }
        } catch (e: Exception) {
            Log.e("******", e.message.toString())
        }
    }

}
@Composable
fun EditBox(profileViewModel: ProfileViewModel) {

    val maxChar = 8
    val db = FirebaseFirestore.getInstance()
    val collectionRef = db.collection("users")
    val userId = Firebase.auth.currentUser?.uid.orEmpty()
    val documentRef = collectionRef.document(userId)

    Log.d(TAG, "firebaseuserid is ${userId}")
    var userInfo by remember { mutableStateOf<MutableMap<String, Any>>(mutableMapOf()) }
    var updatedEmail by remember { mutableStateOf("") }
    var updatedBirthDate by remember { mutableStateOf("") }
    var updatedPhoneNumber by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        try {
            val documentSnapshot = documentRef.get().await()
            if (documentSnapshot != null) {
                val data = documentSnapshot.data
                if (data != null) {
                    userInfo = data.toMutableMap()
                    lastName = userInfo.getValue("lastname").toString()
                    firstName = userInfo.getValue("firstname").toString()
                    updatedEmail = userInfo.getValue("email").toString()
                    updatedBirthDate = userInfo.getValue("dateofbirth").toString()
                    updatedPhoneNumber = userInfo.getValue("phoneNumber").toString()
                }
            }
        } catch (e: Exception) {
            Log.e("******", e.message.toString())
        }
    }



    Box(
        modifier = Modifier
            .padding(vertical = 200.dp)
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)

        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = "First Name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "personIcon"
                    )
                },
                value = firstName, onValueChange = {}, readOnly = true
            )
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = "Last Name") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "personIcon"
                    )
                },
                value = lastName, onValueChange = {}, readOnly = true
            )
            TextField(modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "emailIcon"
                    )
                },
                value = updatedEmail,
                onValueChange = {
                    updatedEmail = it
                }
            )
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                singleLine = true,
                label = { Text(text = "date of birth ") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "dateIcon"
                    )
                },
                value = updatedBirthDate,
                onValueChange = {
                    if (it.length <= maxChar) updatedBirthDate = it
                },
                visualTransformation = Date("##/##/####")
            )
            TextField(
                modifier = Modifier.fillMaxWidth(0.9f), shape = RoundedCornerShape(10.dp),
                label = { Text(text = " phone number ") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "callIcon"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = updatedPhoneNumber,
                onValueChange = { it ->
                    updatedPhoneNumber = it
                }
            )

            Button(
                onClick = {
                    profileViewModel.updateProfile(updatedEmail,updatedBirthDate,updatedPhoneNumber,context)
                    profileViewModel.updateEmail(updatedEmail)
                          },
                modifier = Modifier.width(width = 120.dp)
            ) {
                Row(
                ) {
                    Text(text = "Edit", fontSize = 15.sp)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        painter = painterResource(id = R.drawable.final_logo),
                        contentDescription = "Edit Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }

            }

        }

    }

}