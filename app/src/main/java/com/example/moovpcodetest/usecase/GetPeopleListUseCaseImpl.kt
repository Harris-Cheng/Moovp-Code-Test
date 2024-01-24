package com.example.moovpcodetest.usecase

import com.example.moovpcodetest.model.ui.People
import com.example.moovpcodetest.network.ApiService

class GetPeopleListUseCaseImpl: GetPeopleListUseCase {
    override suspend fun invoke(): List<People> {
        return ApiService.getListOfPeople().orEmpty()
    }
}