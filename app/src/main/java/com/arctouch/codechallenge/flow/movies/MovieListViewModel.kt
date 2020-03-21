package com.arctouch.codechallenge.flow.movies

import androidx.lifecycle.*
import com.arctouch.codechallenge.network.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val repository: MoviesRepository) : ViewModel(),
    LifecycleObserver {

    private val mutableMoviesPresenter: MutableLiveData<MoviesListPresenter> by lazy {
        MutableLiveData<MoviesListPresenter>()
    }

    val liveMoviesPresenter: LiveData<MoviesListPresenter> get() = mutableMoviesPresenter

    private var isSearching = false

    private var hasNextPage = false

    private var currentPage = 1

    private var currentSearchText = ""

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun retrievePopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val moviesResponse = repository.getPopularMovies()

            if (moviesResponse == null || moviesResponse.results.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    mutableMoviesPresenter.value = MoviesListPresenter()
                }
                return@launch
            }

            hasNextPage = currentPage != moviesResponse.totalPages

            withContext(Dispatchers.Main) {
                mutableMoviesPresenter.value = MoviesListPresenter(
                    moviesResponse.results.map { movie ->
                        movie?.let {
                            MoviesListPresenter.MovieListItemPresenter.fromMovie(movie)
                        } ?: kotlin.run {
                            MoviesListPresenter.MovieListItemPresenter()
                        }
                    }
                )
            }

        }
    }

    fun onSearchTextChanged(newSearchText: String) {
        if (newSearchText.isBlank() || newSearchText.length < 3) {
            isSearching = false
            return
        }

        currentSearchText = newSearchText
        isSearching = true
        viewModelScope.launch(Dispatchers.IO) {
            val moviesResponse = repository.getMoviesByName(newSearchText)

            if (moviesResponse == null || moviesResponse.results.isNullOrEmpty()) {
                hasNextPage = false
                withContext(Dispatchers.Main) {
                    mutableMoviesPresenter.value = MoviesListPresenter()
                }
                return@launch
            }

            currentPage = moviesResponse.page ?: 1
            hasNextPage = currentPage != moviesResponse.totalPages

            withContext(Dispatchers.Main) {
                mutableMoviesPresenter.value = MoviesListPresenter(
                    moviesResponse.results.map { movie ->
                        movie.let {
                            MoviesListPresenter.MovieListItemPresenter.fromMovie(movie)
                        }
                    }
                )
            }
        }
    }

    fun onListFinalReached() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isSearching && hasNextPage) {
                val newMovies = repository.getMoviesByName(currentSearchText, currentPage + 1)

                if (newMovies == null || newMovies.results.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        mutableMoviesPresenter.value = MoviesListPresenter()
                    }
                    return@launch
                }

                currentPage = newMovies.page ?: 1
                hasNextPage = currentPage != newMovies.totalPages

                val allMovies = mutableMoviesPresenter.value?.movies?.toMutableList()?.apply {
                    addAll(
                        newMovies.results.map { movie ->
                            movie.let {
                                MoviesListPresenter.MovieListItemPresenter.fromMovie(it)
                            }
                        }
                    )
                }

                withContext(Dispatchers.Main) {
                    mutableMoviesPresenter.value = allMovies?.toList()?.let {
                        MoviesListPresenter(it)
                    }
                }
            } else {
                if (hasNextPage) {
                    val newMovies = repository.getPopularMovies(currentPage + 1)

                    if (newMovies == null || newMovies.results.isNullOrEmpty()) {
                        withContext(Dispatchers.Main) {
                            mutableMoviesPresenter.value = MoviesListPresenter()
                        }
                        return@launch
                    }

                    currentPage = newMovies.page ?: 1
                    hasNextPage = currentPage != newMovies.totalPages

                    val allMovies = mutableMoviesPresenter.value?.movies?.toMutableList()?.apply {
                        addAll(
                            newMovies.results.map { movie ->
                                movie?.let {
                                    MoviesListPresenter.MovieListItemPresenter.fromMovie(it)
                                } ?: kotlin.run {
                                    MoviesListPresenter.MovieListItemPresenter()
                                }
                            }
                        )
                    }

                    withContext(Dispatchers.Main) {
                        mutableMoviesPresenter.value = allMovies?.toList()?.let {
                            MoviesListPresenter(it)
                        }
                    }
                }
            }
        }
    }

}