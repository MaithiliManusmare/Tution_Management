package com.example.assignment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val brand: String,
    val model: String,
    val fuelType: String,
    val vehicleNumber: String,
    val yearOfPurchase: String,
    val ownerName: String
)
