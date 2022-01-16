package com.example.guidomia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guidomia.db.Movie
import com.example.guidomia.db.MovieRepository
import kotlinx.coroutines.*

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllMovies() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllMovies()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(filterMovies(response.body()!!.results))
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun filterMovies(items: List<Movie>): ArrayList<Movie> {
        val movies: ArrayList<Movie> = ArrayList()
        for (item in items) {
            if (item.kind == "feature-movie") {
                movies.add(item)
            }
        }
        return movies
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}