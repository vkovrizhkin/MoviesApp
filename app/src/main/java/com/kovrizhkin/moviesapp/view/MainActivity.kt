package com.kovrizhkin.moviesapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kovrizhkin.moviesapp.MainContract
import com.kovrizhkin.moviesapp.R
import com.kovrizhkin.moviesapp.model.pojo.Movie
import com.kovrizhkin.moviesapp.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var presenter: MainPresenter

    private var movieList: MutableList<Movie> = mutableListOf()

    private lateinit var recViewAdapter: MoviesRecViewAdapter

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recViewAdapter = MoviesRecViewAdapter(this, movieList, null, null)
        layoutManager = LinearLayoutManager(this)

        movies_rec_view.adapter = recViewAdapter
        movies_rec_view.layoutManager = layoutManager

        presenter = MainPresenter(this)

        presenter.loadMovies()
        //presenter.searchMovies("История")
    }

    override fun showResult(result: List<Movie>) {
        movieList.clear()
        movieList.addAll(result)
        recViewAdapter.notifyDataSetChanged()
    }
}
