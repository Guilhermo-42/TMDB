package com.arctouch.codechallenge.flow.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MoviesListAdapter(
    private val listener: OnMovieClickListener
) : RecyclerView.Adapter<MoviesListAdapter.MoviesListViewHolder>() {

    private var movies: MutableList<MoviesListPresenter.MovieListItemPresenter> = mutableListOf()

    private lateinit var context: Context

    fun updateMovies(newMovies: List<MoviesListPresenter.MovieListItemPresenter>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        context = parent.context
        return MoviesListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.movie_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val movie = movies[position]

        holder.apply {
            movieTitle.text = movie.movieTitle
            moviePopularity.text =
                context.getString(R.string.movie_list_item_popularity, movie.moviePopularity)

            movie.movieImagePath?.let {
                Glide.with(context)
                    .load(it)
                    .apply(RequestOptions.centerCropTransform())
                    .into(moviePoster)
            }

            movieDescription.text = movie.movieDescription
            movieDetails.setOnClickListener {
                listener.onItemClicked(movie.movieId)
            }
        }
    }

    inner class MoviesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.movieItemTitleText
        val moviePopularity: TextView = itemView.movieItemPopularityText
        val moviePoster: ImageView = itemView.movieItemPosterImage
        val movieDescription: TextView = itemView.movieItemDescriptionText
        val movieDetails: TextView = itemView.movieItemDetailsText
    }

    interface OnMovieClickListener {

        fun onItemClicked(movieId: String)

    }

}