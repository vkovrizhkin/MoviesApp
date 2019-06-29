package com.kovrizhkin.moviesapp.model.api

import com.kovrizhkin.moviesapp.MainContract.Presenter
import com.kovrizhkin.moviesapp.model.pojo.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLEncoder

object Api {
    private val apiManager = ApiManager()

    fun getMovies(listener: Presenter.GetMoviesListener) {
        val call = apiManager.getService().getMovies()

        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                listener.onGetMoviesError(t)
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val result = response.body()

                if (result != null && response.isSuccessful) {
                    listener.onGetMoviesSuccess(response.body()!!)
                }

            }

        })
    }

    fun getSearchResults(listener: Presenter.SearchMoviesListener, searchText: String) {

        // val encodeSearchText = URLEncoder.encode(searchText, "UTF-8")
        val call = apiManager.getService().search(query = searchText)

        call.enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                listener.onSearchMoviesError(t)
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val result = response.body()
                if (result != null && response.isSuccessful) {
                    listener.onSearchMoviesSuccess(result)
                }
            }

        })
    }
}