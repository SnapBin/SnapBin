package com.example.snapbin.model.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.PropertyName

data class UserSnapReference(
    @PropertyName("id_snap")
    var snapId: DocumentReference
){
    fun getResolverTask(): Task<DocumentSnapshot> {
        return snapId.get()
    }
}
