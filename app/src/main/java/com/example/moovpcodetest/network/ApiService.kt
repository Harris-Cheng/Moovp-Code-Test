package com.example.moovpcodetest.network

import com.example.moovpcodetest.di.Network
import com.example.moovpcodetest.model.ui.People
import com.example.moovpcodetest.model.ui.People.Companion.toModel
import org.koin.java.KoinJavaComponent

object ApiService {
    private val api: Api by KoinJavaComponent.inject(Api::class.java, Network)

    suspend fun getListOfPeople(): List<People>? {
        return api.getListOfPeople().body()?.let {
            it.mapNotNull { response ->
                response.toModel()
            }
        }
    }
}