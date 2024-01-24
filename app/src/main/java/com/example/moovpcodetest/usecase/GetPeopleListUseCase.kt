package com.example.moovpcodetest.usecase

import com.example.moovpcodetest.model.response.PeopleResponse

interface GetPeopleListUseCase {
    suspend operator fun invoke(): List<PeopleResponse>
}