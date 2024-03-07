package com.example.snapbin.model.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class AssociationWritableSnapData(
    @DocumentId
    val id: String,
    @PropertyName("confirmed_urgency")
    var confirmedUrgency: Urgency,
    var status: SnapStatus
)
