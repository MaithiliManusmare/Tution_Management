package com.example.assignment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BatchDao {
    @Insert
    suspend fun insert (batch : Batch)

    @Query("SELECT * FROM Batch")
    fun getAllCourses(): Flow<List<Batch>>
}