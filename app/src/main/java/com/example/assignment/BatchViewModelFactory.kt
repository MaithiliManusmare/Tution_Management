package com.example.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BatchViewModelFactory(
    private val repository: BatchRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BatchViewmodel::class.java) ->
                BatchViewmodel(repository) as T

            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
