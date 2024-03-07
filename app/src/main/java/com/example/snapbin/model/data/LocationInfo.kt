package com.example.snapbin.model.data

import com.snaptrash.snaptrash.model.data.Association

data class LocationInfo(
    var id: String,
    var country: String,
    var region: String,
    var municipality: String,
    var authorizedAssociation: Association?
)
