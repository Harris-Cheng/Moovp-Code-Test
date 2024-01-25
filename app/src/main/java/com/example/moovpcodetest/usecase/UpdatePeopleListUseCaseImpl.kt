package com.example.moovpcodetest.usecase

import com.example.moovpcodetest.model.People.Companion.toModel
import com.example.moovpcodetest.network.ApiService
import com.example.moovpcodetest.room.PeopleDataBase

class UpdatePeopleListUseCaseImpl(
    private val apiService: ApiService,
    private val db: PeopleDataBase
): UpdatePeopleListUseCase {
    override suspend fun invoke() {
        apiService.getListOfPeople()?.mapNotNull { peopleResponse ->
            peopleResponse.toModel()
        }?.also { peoples ->
            db.peopleDao().insertAll(
                peoples
            )
        }
    }
}