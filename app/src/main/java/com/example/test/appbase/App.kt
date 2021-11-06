package com.example.test.appbase

import android.app.Application
import com.example.data.di.repoModule
import com.example.domain.di.useCaseModule
import com.example.test.di.viewModelModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    repoModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}