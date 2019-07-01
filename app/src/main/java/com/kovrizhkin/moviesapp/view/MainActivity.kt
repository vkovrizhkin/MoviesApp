package com.kovrizhkin.moviesapp.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.kovrizhkin.moviesapp.MainContract
import com.kovrizhkin.moviesapp.R
import com.kovrizhkin.moviesapp.model.pojo.Movie
import com.kovrizhkin.moviesapp.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_banner_layout.*

/* TODO что не успел
*  - отрефакторить вёрстку, вынеся стили и макеты отдельно
*
*  - сохранить "любимые" в SharedPreference в GSON строке - простой вариант,
*               положить в локальную бд (realm / SQLite) - немного сложнее вариант (если лайков много)
*
*  - нормально распарсить дату релиза
*  - изменения при смене конфигурации вроде можно было с onSaveInstanceState сделать (сохранить индекс, данные),
 *             но видимо layout manager и без этого сохраняет своё состояние
*  - также для сохранения данных при повороте можно было использовать ViewModel, ещё есть способ с сохранением фрагмента
 *             со ссылками на объекты, но его раньше не пробовал
*  - колбэк тапа на фильм и клика на кнопку "понравилось" пробросить в ресайклер вью и вызывать обработчиками
*
* */

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

        refresh_btn.setOnClickListener { loadAllMovies() }

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

    override fun onError() {
        // todo вынести в ресурсы строчку (тоже самое с сообщением о пустом результате поиска)
        showSnackbar("Проверьте соединение с интернетом и попробуйте ещё раз")
        if (movieList.isEmpty()) {
            showEmptyResult()
        }
        progress_horizontal.visibility = ProgressBar.INVISIBLE
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
    }

    override fun showEmptySearchResult() {
        movieList.clear()
        view_flipper.displayedChild = 3
    }

    private fun showEmptyResult() {
        view_flipper.displayedChild = 2
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
        }

        progress_horizontal.visibility = ProgressBar.INVISIBLE

    }
}
