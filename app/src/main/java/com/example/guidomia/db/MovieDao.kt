package com.example.guidomia.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun insertData(movie: Movie): Long

    @Query("SELECT * FROM movie_data_table")
    fun getAll(): Flow<List<Movie>>

//    @Query("SELECT make FROM cars_data_table")
//    fun getAllMake(): Flow<List<String>>
//
//    @Query("SELECT model FROM cars_data_table")
//    fun getAllModel(): Flow<List<String>>
//
//    @Query("SELECT model FROM cars_data_table WHERE make = :make")
//    fun getAllModel(make: String): Flow<List<String>>
//
//    @Query("SELECT make FROM cars_data_table WHERE model = :model")
//    fun getAllMake(model: String): Flow<List<String>>
}