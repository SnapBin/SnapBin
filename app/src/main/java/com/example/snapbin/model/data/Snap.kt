package com.example.snapbin.model.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.HttpsCallableResult
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URLEncoder
import java.time.ZoneId
import java.util.Date

data class Snap(
    @DocumentId
    val id: String,
    @JvmField
    val location: GeoPoint,
    var user: String,
    @JvmField
    @PropertyName("id_snap_image")
    var snapImageUrl: String,
    @JvmField
    @PropertyName("id_location")
    val locationId: String,
    var date: Date,
    var description: String,
    var urgency: Urgency,
    @JvmField
    @PropertyName("confirmed_urgency")
    var confirmedUrgency: Urgency,
    @JvmField
    var status: SnapStatus
){
    constructor(): this("", GeoPoint(0.0,0.0),"","","", Date(),"",
        Urgency.NOT_URGENT,Urgency.NOT_URGENT,SnapStatus.PENDING)
    fun submitSnap(): Task<HttpsCallableResult> {
        return Firebase.functions.getHttpsCallable("createSnap").call(
            hashMapOf(
                "lat" to location.latitude,
                "lon" to location.longitude,
                "photoURL" to snapImageUrl,
                "urgency" to urgency.name,
                "description" to description
            )
        )
    }
    fun getAssociationWritableData(): Task<QuerySnapshot>{
        return Firebase.firestore.collection("/snaps/${id}/associationWritable").limit(1).get()
    }
    fun encodeForNavigation(): String{
        val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).addLast(
            KotlinJsonAdapterFactory()
        ).build()
        val adapter = moshi.adapter(Snap::class.java)
        val urlEncodedSnap = this.copy(snapImageUrl = URLEncoder.encode(this.snapImageUrl,"UTF-8"))
        return adapter.toJson(urlEncodedSnap)
    }
    fun getFormattedDate(): String{
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        return "${localDate.dayOfMonth}-${"%02d".format((localDate.month.value + 1))}-${(localDate.year)} ${localDate.hour}:${"%02d".format(localDate.minute)} "
    }
}
