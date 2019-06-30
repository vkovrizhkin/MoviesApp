package com.kovrizhkin.moviesapp.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
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

        view_flipper.displayedChild = 1
        movies_rec_view.adapter = recViewAdapter
        movies_rec_view.layoutManager = layoutManager

        presenter = MainPresenter(this)

        loadAllMovies()

        search_edit_text.setOnEditorActionListener { v, actionId, _ ->

            val searchQuery = v.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchQuery.isNotEmpty()) {

                searchMovies(searchQuery)
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        swipe_to_refresh.setOnRefreshListener { onSwipe() }

    }

    private fun loadAllMovies() {
        if (movieList.isEmpty()) {
            view_flipper.displayedChild = 0
        } else {
            progress_horizontal.visibility = ProgressBar.VISIBLE
        }

        presenter.loadMovies()
    }



    private fun onSwipe() {
        swipe_to_refresh.isRefreshing = false
        val searchQuery = search_edit_text.text.toString()
        if (searchQuery.isEmpty()) {
            loadAllMovies()
        } else {
            searchMovies(searchQuery)
        }

    }

    private fun searchMovies(searchQuery: String) {
        if (movieList.isEmpty()) {
            view_flipper.displayedChild = 0
        } else {
            progress_horizontal.visibility = ProgressBar.VISIBLE
        }
        presenter.searchMovies(searchQuery)
    }

    fun showEmptySearchResult() {

    }

    override fun showResult(result: List<Movie>) {
        if (movieList.isEmpty()) {
            movieList.addAll(result)
            recViewAdapter.notifyDataSetChanged()
            view_flipper.displayedChild = 1


        } else {
            movieList.clear()
            movieList.addAll(result)
            recViewAdapter.notifyDataSetChanged()
            progress_horizontal.visibility = ProgressBar.INVISIBLE
        }


    }
}
