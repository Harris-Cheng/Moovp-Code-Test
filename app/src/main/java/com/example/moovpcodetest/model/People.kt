package com.example.moovpcodetest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moovpcodetest.model.response.PeopleResponse

@Entity
data class People(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "latitude")
    val latitude: Double
) {
    companion object {
        fun PeopleResponse.toModel(): People? {
            return People(
                id = _id ?: return null,
                firstName = name?.first.orEmpty(),
                lastName = name?.last.orEmpty(),
                email = email.orEmpty(),
                image = picture.orEmpty(),
                longitude = location?.longitude ?: 0.0,
                latitude = location?.latitude ?: 0.0
            )
        }
    }
}
