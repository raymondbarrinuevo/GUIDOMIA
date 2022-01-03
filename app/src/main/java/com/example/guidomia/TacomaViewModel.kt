package com.example.guidomia

import android.content.Context
import androidx.lifecycle.*
import com.example.guidomia.db.Car
import com.example.guidomia.db.CarRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TacomaViewModel(private val repository: CarRepository) : ViewModel() {

    private val gson = Gson()

    fun getSavedCarsData() = liveData {
        repository.cars.collect {
            emit(it)
        }
    }

    fun readAndSaveCarsData(context: Context) {
        val carList = context.assets.open("cars.txt").bufferedReader().use {
            it.readText()
        }
        val cars = gson.fromJson(carList, Array<Car>::class.java)

        viewModelScope.launch {
            repository.cars.collect {
                if (it.isNullOrEmpty()) {
                    saveCarData(cars)
                }
            }
        }
    }

    fun saveCarData(cars: Array<Car>) =
        viewModelScope.launch {
            for (car in cars) {
                repository.insert(car)
            }
        }

    fun getAllMake() = liveData {
        repository.allMake.collect {
            emit(it)
        }
    }

    fun getAllModel() = liveData {
        repository.allModel.collect {
            emit(it)
        }
    }
}