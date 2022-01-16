package com.example.guidomia.db

import com.example.guidomia.network.RetrofitService

class MovieRepository(private val retrofitService: RetrofitService) {

    suspend fun getAllMovies() = retrofitService.getAllMovies()
}