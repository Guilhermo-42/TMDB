package com.arctouch.codechallenge.network

import com.arctouch.codechallenge.network.model.details.MovieDetailsResponse
import com.arctouch.codechallenge.network.model.popular.PopularMoviesResponse
import com.arctouch.codechallenge.network.model.search.SearchMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): PopularMoviesResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(@Path("movieId") movieId: String): MovieDetailsResponse

    @GET("search/movie")
    suspend fun getMoviesByName(
        @Query("query") movieName: String,
        @Query("page") page: Int = 1
    ): SearchMovieResponse

}