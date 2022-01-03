package com.example.guidomia.db

import kotlinx.coroutines.flow.Flow

class CarRepository(private val dao: CarDao) {

    val cars = dao.getAllCars()

    val allMake = dao.getAllMake()

    val allModel = dao.getAllModel()

    suspend fun insert(car: Car): Long {
        return dao.insertCarData(car)
    }
}