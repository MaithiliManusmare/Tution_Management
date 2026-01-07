package com.example.assignment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentDao {

    @Insert
    suspend fun insert(student: Student)

    @Query
    ("SELECT * FROM student") fun getAllStudents(): Flow<List<Student>>

}