package com.example.moovpcodetest.model.ui

import com.example.moovpcodetest.model.response.PeopleResponse

data class People(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String,
    val long: Double,
    val lad: Double
) {
    companion object {
        fun PeopleResponse.toModel(): People? {
            return People(
                id = _id ?: return null,
                firstName = name?.first.orEmpty(),
                lastName = name?.last.orEmpty(),
                email = email.orEmpty(),
                image = picture.orEmpty(),
                long = location?.longitude ?: 0.0,
                lad = location?.latitude ?: 0.0
            )
        }
    }
}
