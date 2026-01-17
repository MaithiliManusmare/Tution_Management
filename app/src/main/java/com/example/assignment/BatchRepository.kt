package com.example.assignment

import kotlinx.coroutines.flow.Flow

class BatchRepository(private val batchDao: BatchDao) {
    val allBatches: Flow<List<Batch>> = batchDao.getAllCourses()

    suspend fun insert(batch: Batch) {
        batchDao.insert(batch)
    }

}