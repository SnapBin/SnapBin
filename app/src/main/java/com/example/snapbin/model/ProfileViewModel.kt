package com.example.snapbin.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileViewModel(
) : ViewModel() {

    val userId = Firebase.auth.currentUser?.uid.orEmpty()

    fun updateProfile(
        birthDate:String,
        phoneNumber:String,
        firstname:String, lastname: String,
        context: Context){
        val db = Firebase.firestore
        val collectionRef = db.collection("users") //
        val documentRef = collectionRef.document(userId)

        val updatedData = hashMapOf(
            "phoneNumber" to phoneNumber,
            "dateofbirth" to birthDate,
            "firstname" to firstname,
            "lastname" to lastname
        )
        documentRef
            .update(updatedData as Map<String, Any>)
            .addOnSuccessListener {
                showToast("Profile is updated ",context)
                Log.d("******" , " Infos are updated ")
            }
            .addOnFailureListener { e ->
                Log.e("******",e.message.toString())
            }

    }
    fun showToast(message: String,context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}