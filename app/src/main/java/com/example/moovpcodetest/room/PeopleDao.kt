package com.example.moovpcodetest.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moovpcodetest.model.ui.People
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people")
    fun getAll(): Flow<List<People>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(peoples: List<People>)
}