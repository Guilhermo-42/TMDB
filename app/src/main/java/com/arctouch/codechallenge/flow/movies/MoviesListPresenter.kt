package com.arctouch.codechallenge.flow.movies

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.network.model.Movie
import java.io.Serializable

data class MoviesListPresenter(

    val movies: List<MovieListItemPresenter> = emptyList()

) : Serializable {
    class MovieListItemPresenter(
        val movieId: String = "",
        val movieTitle: String = "",
        val moviePopularity: String = "",
        val movieImagePath: String? = null,
        val movieDescription: String = ""
    ) : Serializable {

        companion object {
            fun fromMovie(movie: Movie): MovieListItemPresenter {
                return MovieListItemPresenter(
                    movie.id.toString(),
                    movie.originalTitle ?: "",
                    movie.popularity.toString(),
                    BuildConfig.BASE_IMAGE_URL + movie.posterPath,
                    movie.overview ?: ""
                )
            }
        }

    }
}