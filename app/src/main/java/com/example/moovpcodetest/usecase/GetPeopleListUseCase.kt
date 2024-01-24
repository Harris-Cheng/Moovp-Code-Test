package com.example.moovpcodetest.usecase

import com.example.moovpcodetest.model.ui.People

interface GetPeopleListUseCase {
    suspend operator fun invoke(): List<People>
}