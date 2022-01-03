package com.example.guidomia.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Insert
    suspend fun insertCarData(car: Car): Long

    @Query("SELECT * FROM cars_data_table")
    fun getAllCars(): Flow<List<Car>>

    @Insert
    fun insertCarsData(objects: Array<Car>): Array<Long>
}