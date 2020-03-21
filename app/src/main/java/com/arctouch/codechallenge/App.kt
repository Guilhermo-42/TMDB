package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.di.networkModule
import com.arctouch.codechallenge.di.repositoryModule
import com.arctouch.codechallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

}