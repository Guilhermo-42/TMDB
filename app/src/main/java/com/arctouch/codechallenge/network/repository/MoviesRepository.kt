package com.arctouch.codechallenge.network.repository

import android.util.Log
import com.arctouch.codechallenge.network.TmdbApi
import com.arctouch.codechallenge.network.model.details.MovieDetailsResponse
import com.arctouch.codechallenge.network.model.popular.PopularMoviesResponse
import com.arctouch.codechallenge.network.model.search.SearchMovieResponse

class MoviesRepository(private val api: TmdbApi) : IMoviesRepository {

    override suspend fun getPopularMovies(page: Int): PopularMoviesResponse? {
        return try {
            api.getPopularMovies(page)
        } catch (e: Exception) {
            Log.e(MoviesRepository::class.java.name, e.toString())
            null
        }
    }

    override suspend fun getMovieDetails(movieId: String): MovieDetailsResponse? {
        return try {
            api.getMovieDetails(movieId)
        } catch (e: Exception) {
            Log.e(MoviesRepository::class.java.name, e.toString())
            null
        }
    }

    override suspend fun getMoviesByName(name: String, page: Int): SearchMovieResponse? {
        return try {
            api.getMoviesByName(name, page)
        } catch (e: Exception) {
            Log.e(MoviesRepository::class.java.name, e.toString())
            null
        }
    }

}