package com.kovrizhkin.moviesapp.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    var apiService: ApiService? = null

    fun getService(): ApiService {
        if(apiService==null){
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService!!
    }

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}