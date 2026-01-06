package com.example.assignment

import kotlinx.coroutines.flow.Flow

class VehicleRepository(private val vehicleDao: VehicleDao) {

    val allVehicles: Flow<List<Student>> = vehicleDao.getAllStudents()

    suspend fun insert(student: Student) {
        vehicleDao.insert(student)
    }

}
