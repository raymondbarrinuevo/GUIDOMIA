package com.example.guidomia

import android.content.Context
import androidx.lifecycle.*
import com.example.guidomia.db.Car
import com.example.guidomia.db.CarRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TacomaViewModel(private val repository: CarRepository) : ViewModel() {

    private val gson = Gson()
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    private val itemList = MutableLiveData<Event<Array<Car>>>()
    val itemCars: LiveData<Event<Array<Car>>>
        get() = itemList

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

        itemList.value = Event(cars)
//        saveCarData(cars)
    }

    fun saveCarData(cars: Array<Car>) = viewModelScope.launch {
        viewModelScope.launch {
            val newRowId = repository.insertCarList(cars)

            if (newRowId.isNotEmpty()) {
                statusMessage.value = Event("Cars Data Inserted Successfully $newRowId")
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }

    }

}