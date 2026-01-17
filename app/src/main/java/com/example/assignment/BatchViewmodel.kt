package com.example.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BatchViewmodel(private val repository: BatchRepository): ViewModel(){
    val allBatch : Flow<List<Batch>> = repository.allBatches

    fun insert(batch : Batch){
        viewModelScope.launch {
            try {
                repository.insert(batch)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}