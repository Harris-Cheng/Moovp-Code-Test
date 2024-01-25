package com.example.moovpcodetest.network

import com.example.moovpcodetest.model.response.PeopleResponse

class ApiService(
    private val api: Api
) {
    suspend fun getListOfPeople(): List<PeopleResponse>? {
        return try {
            api.getListOfPeople().body()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}