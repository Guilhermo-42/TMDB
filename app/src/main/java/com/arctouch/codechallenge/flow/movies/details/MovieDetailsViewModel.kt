package com.arctouch.codechallenge.flow.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arctouch.codechallenge.network.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val mutablePresenter: MutableLiveData<MovieDetailsPresenter> by lazy {
        MutableLiveData<MovieDetailsPresenter>()
    }

    val livePresenter: LiveData<MovieDetailsPresenter> get() = mutablePresenter

    fun retrieveMovieDetails(movieId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieDetailsResponse = repository.getMovieDetails(movieId)

            if (movieDetailsResponse == null) {
                withContext(Dispatchers.Main) {
                    mutablePresenter.value = MovieDetailsPresenter()
                }
                return@launch
            }

            withContext(Dispatchers.Main) {
                mutablePresenter.value =
                    MovieDetailsPresenter.fromMovieDetails(movieDetailsResponse)
            }

        }
    }

}