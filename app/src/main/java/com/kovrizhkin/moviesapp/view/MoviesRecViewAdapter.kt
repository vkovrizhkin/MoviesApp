package com.kovrizhkin.moviesapp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kovrizhkin.moviesapp.R
import com.kovrizhkin.moviesapp.model.pojo.Movie
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MoviesRecViewAdapter(
        private val context: Context,
        private val movieList: List<Movie>,
        val onPressItem: ((Movie) -> Any)?,
        val onPressFavorite: ((Movie) -> Any)?
) : RecyclerView.Adapter<MoviesRecViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false) as View
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]

        holder.titleTextView.text = movie.title
        holder.descriptionTextView.text = movie.overview

        Glide
                .with(context)
                .load("https://image.tmdb.org/t/p/w200" + movie.poster_path)
                .centerCrop()
                .into(holder.image)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.title_text_view!!
        val image = view.poster_image_view!!
        val descriptionTextView = view.description_text_view!!
        val dateTextView = view.date_text_view!!
    }
}