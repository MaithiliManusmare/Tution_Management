package com.example.assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val dob: Long,
    val gender: String,
    val grade: Int,
    val board: String,
    val subjects: String,
    val guardianName: String,
    val guardianMobNumber: String,
    val studentMobNumber: String,
    val batchName: String,
    val joiningDate: String,
    val feeAmount: Int,
    val lastYearPercent: String
)
