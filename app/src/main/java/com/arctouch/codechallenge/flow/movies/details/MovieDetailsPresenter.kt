package com.arctouch.codechallenge.flow.movies.details

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.extensions.minutesToHoursAndMinutes
import com.arctouch.codechallenge.network.model.details.MovieDetailsResponse
import java.io.Serializable

data class MovieDetailsPresenter(

    val moviePosterPath: String? = null,
    val movieName: String = "",
    val movieCompanyImage: String? = null,
    val movieTagline: String = "",
    val moviePopularity: String = "",
    val movieReleaseDate: String = "",
    val movieVoteAverage: String = "",
    val movieRuntime: String = "",
    val movieDescription: String = ""

) : Serializable {
    companion object {
        fun fromMovieDetails(model: MovieDetailsResponse): MovieDetailsPresenter {
            return MovieDetailsPresenter(
                BuildConfig.BASE_IMAGE_URL + model.posterPath,
                model.title ?: "",
                model.productionCompanies?.firstOrNull { it.logoPath != null }?.logoPath,
                model.tagline ?: "",
                model.popularity.toString(),
                model.releaseDate.toString(),
                model.voteAverage.toString(),
                model.runtime?.minutesToHoursAndMinutes() ?: "",
                model.overview ?: ""
            )
        }

    }
}