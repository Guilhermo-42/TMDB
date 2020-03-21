package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.network.repository.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MoviesRepository(get()) }
}