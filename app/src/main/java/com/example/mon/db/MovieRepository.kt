package com.example.mon.db

import com.example.mon.network.RetrofitService

class MovieRepository(private val retrofitService: RetrofitService, private val dao: MovieDao) {

    suspend fun getAllMovies() = retrofitService.getAllMovies()

    val movies = dao.getAll()

    suspend fun insert(movies: List<Movie>): List<Long> {
        return dao.insertAllData(movies)
    }
}