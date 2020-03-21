package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.flow.movies.MovieListViewModel
import com.arctouch.codechallenge.flow.movies.details.MovieDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}