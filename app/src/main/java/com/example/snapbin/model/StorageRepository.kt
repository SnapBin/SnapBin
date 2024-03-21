package com.example.snapbin.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class StorageRepository {

    fun user() = Firebase.auth.currentUser
    fun hasUser() : Boolean = Firebase.auth.currentUser != null
    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()


}


