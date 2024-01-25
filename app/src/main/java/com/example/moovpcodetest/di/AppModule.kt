package com.example.moovpcodetest.di

import com.example.moovpcodetest.network.ApiService
import com.example.moovpcodetest.usecase.UpdatePeopleListUseCase
import com.example.moovpcodetest.usecase.UpdatePeopleListUseCaseImpl
import com.example.moovpcodetest.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factoryOf(::UpdatePeopleListUseCaseImpl) bind UpdatePeopleListUseCase::class

    singleOf(::ApiService)

    viewModelOf(::MainViewModel)
}