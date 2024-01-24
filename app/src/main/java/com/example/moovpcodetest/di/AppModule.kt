package com.example.moovpcodetest.di

import com.example.moovpcodetest.network.ApiService
import com.example.moovpcodetest.usecase.GetPeopleListUseCase
import com.example.moovpcodetest.usecase.GetPeopleListUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factoryOf(::GetPeopleListUseCaseImpl) bind GetPeopleListUseCase::class

    single {
        ApiService(
            get(Network),
            get()
        )
    }
}