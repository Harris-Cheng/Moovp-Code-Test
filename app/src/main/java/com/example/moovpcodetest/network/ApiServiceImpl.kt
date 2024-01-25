package com.example.moovpcodetest.network

import com.example.moovpcodetest.model.response.PeopleResponse

class ApiServiceImpl(
    private val api: Api
): ApiService {
    override suspend fun getListOfPeople(): List<PeopleResponse>? {
        return try {
            api.getListOfPeople().body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}