package com.example.moovpcodetest.di

import androidx.room.Room
import com.example.moovpcodetest.room.PeopleDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            PeopleDataBase::class.java, "people-database"
        ).build()
    }
}