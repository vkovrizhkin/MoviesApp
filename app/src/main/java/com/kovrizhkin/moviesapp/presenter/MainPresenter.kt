package com.kovrizhkin.moviesapp.presenter

import com.kovrizhkin.moviesapp.MainContract.Presenter
import com.kovrizhkin.moviesapp.model.api.Api
import com.kovrizhkin.moviesapp.model.pojo.Result


class MainPresenter: Presenter.GetMoviesListener, Presenter.SearchMoviesListener {
    override fun onGetMoviesSuccess(result: Result) {
        val r = result
    }

    override fun onGetMoviesError(t: Throwable) {
        val r = t
    }

    override fun onSearchMoviesSuccess(result: Result) {
        val r = result
    }

    override fun onSearchMoviesError(t: Throwable) {
        val r = t
    }


    fun loadMovies(){
        Api.getMovies(this)
    }

    fun searchMovies(searchText: String) {
        Api.getSearchResults(this, searchText)
    }
}