package com.cihat.egitim.composejokeapp.service

import com.cihat.egitim.composejokeapp.model.Joke
import retrofit2.Response
import retrofit2.http.GET

interface JokeAPI {
    @GET("atilsamancioglu/ProgrammingJokesJSON/refs/heads/main/jokes.json")
    suspend fun getData(): Response<List<Joke>>
}