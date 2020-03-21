package com.arctouch.codechallenge

import com.arctouch.codechallenge.network.TmdbApi
import com.arctouch.codechallenge.network.model.popular.PopularMoviesResponse
import com.arctouch.codechallenge.network.repository.MoviesRepository
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Spy

class MoviesRepositoryUnitTest {

    @Mock
    lateinit var repository: MoviesRepository

    @Spy
    lateinit var api: TmdbApi

    @Before
    fun init() {
        api = spy()
        repository = mock() {
            onBlocking {
                getPopularMovies()
            } doReturn PopularMoviesResponse()
        }
    }

    @Test
    fun get_popular_movies_calls_api() {
        runBlocking {
            repository.getPopularMovies()
            verify(api).getPopularMovies()
        }
    }

    @Test
    fun get_popular_movies_api_ok_returns_response() {
        runBlocking {
            val data = repository.getPopularMovies()
            Assert.assertEquals(data, PopularMoviesResponse())
        }
    }

}