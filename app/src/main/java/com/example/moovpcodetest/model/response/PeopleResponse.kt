package com.example.moovpcodetest.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PeopleResponse(
    val _id: String?,
    val name: Name?,
    val email: String?,
    val picture: String?,
    val location: Location?
) {
    @JsonClass(generateAdapter = true)
    data class Name(
        val last: String?,
        val first: String?,
    )

    @JsonClass(generateAdapter = true)
    data class Location(
        val latitude: Double?,
        val longitude: Double?,
    )
}
