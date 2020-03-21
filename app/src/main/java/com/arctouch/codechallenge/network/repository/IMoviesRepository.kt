package com.arctouch.codechallenge.network.repository

import com.arctouch.codechallenge.network.model.details.MovieDetailsResponse
import com.arctouch.codechallenge.network.model.popular.PopularMoviesResponse
import com.arctouch.codechallenge.network.model.search.SearchMovieResponse

interface IMoviesRepository {

    /**
     * Returns a List of popular movies.
     *
     * @return [PopularMoviesResponse] object.
     **/
    suspend fun getPopularMovies(page: Int = 1): PopularMoviesResponse?

    /**
     * Returns details of a given movie given its id.
     *
     * @return [MovieDetailsResponse] object.
     **/
    suspend fun getMovieDetails(movieId: String): MovieDetailsResponse?

    /**
     * Returns a list of movies given a name.
     *
     * @return [SearchMovieResponse] object.
     **/
    suspend fun getMoviesByName(name: String, page: Int = 1): SearchMovieResponse?
}