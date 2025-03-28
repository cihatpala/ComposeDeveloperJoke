package com.cihat.egitim.composejokeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cihat.egitim.composejokeapp.model.Joke
import com.cihat.egitim.composejokeapp.service.JokeAPI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JokeViewModel(application: Application) : AndroidViewModel(application) {
    //https://raw.githubusercontent.com/atilsamancioglu/ProgrammingJokesJSON/refs/heads/main/jokes.json
    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> get() = _jokes

    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JokeAPI::class.java)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }

    fun fetchJokes() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = retrofit.getData()
            if (response.isSuccessful) {
                response.body()?.let { jokesList ->
                    withContext(Dispatchers.Main) {
                        _jokes.value = jokesList
                    }
                }
            }
        }
    }
}