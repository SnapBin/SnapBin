package com.snaptrash.snaptrash.model.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.util.*

data class Association(
    @DocumentId
    val id: String,
    var name : String,
    @PropertyName("phone_number")
    var phoneNumber : String,
    var email : String,
    var type : String,
    var country : String,
    var city : String,
    @PropertyName("zip_code")
    var zipCode : String,
    @PropertyName("street_number")
    var streetNumber : String,
)
