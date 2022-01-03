package com.example.guidomia.db

class CarRepository(private val dao: CarDao) {

    val cars = dao.getAllCars()

    suspend fun insert(car: Car): Long {
        return dao.insertCarData(car)
    }

    suspend fun insertCarList(cars: Array<Car>): Array<Long> {
        return dao.insertCarsData(cars)
    }
}