package com.example.moovpcodetest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.moovpcodetest.model.People
import com.example.moovpcodetest.model.ui.PeopleUIModel
import com.example.moovpcodetest.room.PeopleDataBase
import com.example.moovpcodetest.usecase.UpdatePeopleListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    app: Application,
    updateApiUseCase: UpdatePeopleListUseCase,
    db: PeopleDataBase
): AndroidViewModel(app) {

    init {
        // request api on init
        viewModelScope.launch(Dispatchers.IO) {
            updateApiUseCase()
        }
    }

    private val mapReady = MutableStateFlow(false)

    // start observing from room db,
    // and emit value when map ready,
    // omit value if not changed
    val peopleDaoFlow = mapReady.combineTransform(db.peopleDao().getAll()) { mapReady, list ->
        if (mapReady) {
            emit(list)
        }
    }.distinctUntilChanged().shareIn(
        viewModelScope, SharingStarted.Eagerly, 0
    )

    private val selectedPeople = MutableStateFlow<String?>(null)

    val peopleListFlow: StateFlow<List<PeopleUIModel>> = peopleDaoFlow.combine(selectedPeople) { list, selected ->
        if (selected != null) {
            buildList<PeopleUIModel> {
                list.find {
                    it.id == selected
                }?.also {
                    add(
                        PeopleUIModel.DetailItem(
                            it
                        )
                    )
                }
                addAll(
                    list.mapNotNull {
                        if (it.id == selected) {
                            null
                        } else {
                            PeopleUIModel.ListItem(it)
                        }
                    }
                )
            }
        } else {
            list.map {
                PeopleUIModel.ListItem(it)
            }
        }
    }.stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

    fun onMapReady() {
        mapReady.value = true
    }

    fun updateSelectedItem(item: People) {
        selectedPeople.value = item.id
    }

    fun onMarkerClick(title: String) {
        peopleListFlow.value.find {
            it.item.fullName == title
        }?.also {
            selectedPeople.value = it.item.id
        }
    }

}