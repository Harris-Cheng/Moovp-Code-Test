package com.example.moovpcodetest.network

import com.example.moovpcodetest.model.response.PeopleResponse

interface ApiService {
    suspend fun getListOfPeople(): List<PeopleResponse>?
}