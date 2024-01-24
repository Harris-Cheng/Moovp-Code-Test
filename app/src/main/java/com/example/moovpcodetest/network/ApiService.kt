package com.example.moovpcodetest.network

import com.example.moovpcodetest.model.response.PeopleResponse
import com.example.moovpcodetest.model.ui.People.Companion.toModel
import com.example.moovpcodetest.room.PeopleDataBase

class ApiService(
    private val api: Api,
    private val db: PeopleDataBase
) {
    suspend fun getListOfPeople(): List<PeopleResponse>? {
        return api.getListOfPeople().body()?.also {
            it.mapNotNull { response ->
                response.toModel()
            }.also { peoples ->
                db.peopleDao().insertAll(
                    peoples
                )
            }
        }
    }
}