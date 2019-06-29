package com.kovrizhkin.moviesapp.model.api

import com.kovrizhkin.moviesapp.model.pojo.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/{type}")
    fun getMovies(
        @Path("type") type: String = TYPE,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANGUAGE
    ): Call<Result>


    @GET("search/{type}")
    fun search(
        @Path("type") type: String = TYPE,
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") lang: String = LANGUAGE
    ): Call<Result>

    companion object {
        const val LANGUAGE = "ru"
        const val API_KEY = "6ccd72a2a8fc239b13f209408fc31c33"
        const val TYPE = "movie"
    }
}