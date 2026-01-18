package com.example.assignment

import StudentViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StudentViewModelFactory(
    private val repository: StudentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(StudentViewModel::class.java) ->
                StudentViewModel(repository) as T

            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
