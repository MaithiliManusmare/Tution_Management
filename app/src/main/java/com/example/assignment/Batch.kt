package com.example.assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Batch")
data class Batch(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    val subjects: String,
    val startTime: Long,
    val endTime: Long
)
