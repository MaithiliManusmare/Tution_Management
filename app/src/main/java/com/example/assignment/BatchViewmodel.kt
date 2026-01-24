package com.example.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

class BatchViewmodel(private val batchRepository: BatchRepository,
                     private val studentRepository: StudentRepository): ViewModel(){
    val allBatch : Flow<List<Batch>> = batchRepository.allBatches
    val allStudents: Flow<List<Student>> = studentRepository.allStudents


    fun insert(batch : Batch){
        viewModelScope.launch {
            try {
                batchRepository.insert(batch)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getStudentCount(batchName: String): Int {
        return studentRepository.getStudentCountPerBatch(batchName)
    }

}