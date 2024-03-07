package com.example.snapbin.model.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.util.Date

data class PrivateUserData(
    @DocumentId
    val id: String,
    @PropertyName("birth_date")
    var birthDate: Date,
    var gender: Gender,
    @PropertyName("phone_number")
    var phoneNumber: String,
    var email: String
)
