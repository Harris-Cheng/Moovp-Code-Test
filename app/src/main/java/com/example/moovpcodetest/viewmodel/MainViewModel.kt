package com.example.moovpcodetest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovpcodetest.network.ApiService
import com.example.moovpcodetest.room.PeopleDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MainViewModel(
    app: Application,
    apiService: ApiService,
    db: PeopleDataBase
): AndroidViewModel(app) {

    init {
        // request api on init
        viewModelScope.launch(Dispatchers.IO) {
            apiService.getListOfPeople()
        }
    }

    private val mapReady = MutableStateFlow(false)

    // start observing from room db,
    // and emit value when map ready,
    // omit value if not changed
    val peopleListFlow = mapReady.combineTransform(db.peopleDao().getAll()) { mapReady, list ->
        if (mapReady) {
            emit(list)
        }
    }.distinctUntilChanged().shareIn(
        viewModelScope, SharingStarted.Eagerly, 1
    )

    fun onMapReady() {
        mapReady.value = true
    }

}