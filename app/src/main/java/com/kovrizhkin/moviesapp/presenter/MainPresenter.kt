package com.kovrizhkin.moviesapp.presenter

import android.content.Context
import com.kovrizhkin.moviesapp.MainContract
import com.kovrizhkin.moviesapp.MainContract.Presenter
import com.kovrizhkin.moviesapp.model.SharedPreferenceManager
import com.kovrizhkin.moviesapp.model.api.Api
import com.kovrizhkin.moviesapp.model.pojo.Result


class MainPresenter(private val view: MainContract.View) : Presenter.GetMoviesListener, Presenter.SearchMoviesListener {
    override fun onGetMoviesSuccess(result: Result) {
        view.showResult(result.results)
    }

    override fun onGetMoviesError(t: Throwable) {
        view.onError()
    }

    override fun onSearchMoviesSuccess(result: Result) {
        val movies = result.results
        if (movies.isEmpty()) {
            view.showEmptySearchResult()
        } else {
            view.showResult(movies)
        }

    }

    override fun onSearchMoviesError(t: Throwable) {
        view.onError()
    }


    fun loadMovies() {
        Api.getMovies(this)
    }

    fun searchMovies(searchText: String) {
        Api.getSearchResults(this, searchText)
    }

    fun saveFavorites(idList: List<Int>) {
        SharedPreferenceManager.saveFavorites(view as Context, idList)
    }

    fun loadFavorites(): List<Int> {
        return SharedPreferenceManager.getFavorites(view as Context)
    }


}