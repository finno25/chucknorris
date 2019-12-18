package com.rafinno.chucknorris.networks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    //https://api.chucknorris.io/jokes/categories
    @get:GET("jokes/categories")
    val categories: Call<List<String>>

    //https://api.chucknorris.io/jokes/random?category=travel
    @GET("jokes/random")
    fun getRandomJokesFromCategory(@Query("category") category: String): Call<Jokes>

    //https://api.chucknorris.io/jokes/search?query=Rome
    @GET("jokes/search")
    fun getSearchJokes(@Query("query") query: String): Call<SearchJokes>
}