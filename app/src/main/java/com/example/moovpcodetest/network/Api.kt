package com.example.moovpcodetest.network

import com.example.moovpcodetest.model.response.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {
    @Headers("Authorization: Bearer b2atclr0nk1po45amg305meheqf4xrjt9a1bo410")
    @GET("templates/-xdNcNKYtTFG/data")
    suspend fun getListOfPeople(): Response<List<PeopleResponse>>
}