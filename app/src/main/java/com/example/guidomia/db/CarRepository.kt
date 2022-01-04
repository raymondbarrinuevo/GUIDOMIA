package com.example.guidomia.db

import kotlinx.coroutines.flow.Flow

class CarRepository(private val dao: CarDao) {

    val cars = dao.getAllCars()

    val allMake = dao.getAllMake()

    val allModel = dao.getAllModel()

    suspend fun insert(car: Car): Long {
        return dao.insertCarData(car)
    }

    fun getAllModel(make: String): Flow<List<String>> {
        return dao.getAllModel(make)
    }

    fun getAllMake(model: String): Flow<List<String>> {
        return dao.getAllMake(model)
    }
}