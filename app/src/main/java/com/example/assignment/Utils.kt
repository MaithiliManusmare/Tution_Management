package com.example.assignment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toReadableTime(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}
