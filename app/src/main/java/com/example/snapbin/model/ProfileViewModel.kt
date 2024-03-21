package com.example.snapbin.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileViewModel(
) : ViewModel() {

    val userId = Firebase.auth.currentUser?.uid.orEmpty()

    fun updateProfile(email:String,birthDate:String,phoneNumber:String,context: Context){
        val db = Firebase.firestore
        val collectionRef = db.collection("users") //
        val documentRef = collectionRef.document(userId)

        val updatedData = hashMapOf(
            "email" to email,
            "phoneNumber" to phoneNumber,
            "dateofbirth" to birthDate
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

    fun updateEmail(newEmail: String) {

        if (newEmail.isNotEmpty()) {
            val user = FirebaseAuth.getInstance().currentUser

            user?.updateEmail(newEmail)
                ?.addOnSuccessListener {
                    Log.d("******", " Email is updated ")
                }
                ?.addOnFailureListener { e ->
                    Log.e("******", e.message.toString())
                }
        }else{
            Log.e("******","email is empty")
        }
    }

    fun showToast(message: String,context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}