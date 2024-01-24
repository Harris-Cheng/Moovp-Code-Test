package com.example.moovpcodetest

import android.app.Application
import com.example.moovpcodetest.di.appModule
import com.example.moovpcodetest.di.networkModule
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                networkModule,
                appModule
            )
        }
    }
}