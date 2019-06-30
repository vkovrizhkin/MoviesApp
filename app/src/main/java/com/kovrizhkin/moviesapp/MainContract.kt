package com.kovrizhkin.moviesapp

import com.kovrizhkin.moviesapp.model.pojo.Movie
import com.kovrizhkin.moviesapp.model.pojo.Result

interface MainContract {
    interface Presenter {
        interface GetMoviesListener {
            fun onGetMoviesSuccess(result: Result)
            fun onGetMoviesError(t: Throwable)
        }

        interface SearchMoviesListener {
            fun onSearchMoviesSuccess(result: Result)
            fun onSearchMoviesError(t: Throwable)
        }
    }

    interface View {
        fun showResult(result: List<Movie>)
    }
}