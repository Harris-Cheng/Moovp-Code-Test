package com.example.moovpcodetest.model.ui

import com.example.moovpcodetest.model.People

sealed interface PeopleUIModel {
    val item: People

    data class ListItem(
        override val item: People,
    ): PeopleUIModel

    data class DetailItem(
        override val item: People,
    ): PeopleUIModel
}