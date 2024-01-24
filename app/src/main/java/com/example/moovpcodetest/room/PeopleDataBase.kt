package com.example.moovpcodetest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moovpcodetest.model.ui.People

@Database(entities = [People::class], version = 1)
abstract class PeopleDataBase: RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}