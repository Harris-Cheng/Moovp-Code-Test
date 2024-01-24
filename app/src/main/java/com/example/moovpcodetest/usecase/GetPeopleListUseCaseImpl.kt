package com.example.moovpcodetest.usecase

import com.example.moovpcodetest.model.response.PeopleResponse
import com.example.moovpcodetest.network.ApiService

class GetPeopleListUseCaseImpl(
    private val apiService: ApiService
): GetPeopleListUseCase {
    override suspend fun invoke(): List<PeopleResponse> {
        return apiService.getListOfPeople().orEmpty()
    }
}