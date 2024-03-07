package com.example.snapbin.model.data

import java.util.Date

data class Report(
    var id: String,
    var date: Date,
    var reporterId: String,
    var reportedId: String,
    var message: String?,
    var status: ReportStatus
)
