package com.example.assignment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface VehicleDao {

    @Insert
    suspend fun insert(vehicle: Vehicle)

    @Query
    ("SELECT * FROM vehicles") fun getAllVehicles(): Flow<List<Vehicle>>

}