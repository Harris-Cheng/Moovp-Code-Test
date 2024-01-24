package com.example.moovpcodetest.di

import com.example.moovpcodetest.usecase.GetPeopleListUseCase
import com.example.moovpcodetest.usecase.GetPeopleListUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factoryOf(::GetPeopleListUseCaseImpl) bind GetPeopleListUseCase::class
}