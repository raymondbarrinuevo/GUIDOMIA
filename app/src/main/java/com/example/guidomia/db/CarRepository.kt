package com.example.guidomia.db

class CarRepository(private val dao: CarDao) {

    val cars = dao.getAllCars()

    suspend fun insert(car: Car): Long {
        return dao.insertCarData(car)
    }
}