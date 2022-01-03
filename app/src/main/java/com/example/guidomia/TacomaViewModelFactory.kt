package com.example.guidomia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guidomia.db.CarRepository

class TacomaViewModelFactory(private val repository: CarRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TacomaViewModel::class.java)) {
            return TacomaViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model class")
    }
}