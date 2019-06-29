package com.kovrizhkin.moviesapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kovrizhkin.moviesapp.R
import com.kovrizhkin.moviesapp.presenter.MainPresenter

class MainActivity : AppCompatActivity() {

    private lateinit var presenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter()

        // presenter.loadMovies()
        presenter.searchMovies("История")
    }
}
