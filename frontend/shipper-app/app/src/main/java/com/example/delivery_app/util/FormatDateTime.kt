package com.example.delivery_app.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

class FormatDateTime {
    fun formattedDateTime(timestamp: Timestamp): String {
        val date = Date(timestamp.time)
        return SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date)
    }
}