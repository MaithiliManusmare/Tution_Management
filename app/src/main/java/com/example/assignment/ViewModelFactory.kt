package com.example.assignment

import VehicleViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class viewModelFactory(private val repository: VehicleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VehicleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

