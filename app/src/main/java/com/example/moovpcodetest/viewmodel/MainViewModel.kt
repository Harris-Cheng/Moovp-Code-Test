package com.example.moovpcodetest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovpcodetest.network.ApiService
import com.example.moovpcodetest.room.PeopleDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    app: Application,
    apiService: ApiService,
    private val db: PeopleDataBase
): AndroidViewModel(app) {

    init {
        // request api on init
        viewModelScope.launch(Dispatchers.IO) {
            apiService.getListOfPeople()
        }
    }

    // start observing from room db
    val peopleListFlow = db.peopleDao().getAll().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

}