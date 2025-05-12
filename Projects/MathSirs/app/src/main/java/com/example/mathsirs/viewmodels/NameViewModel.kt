package com.example.mathsirs.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathsirs.repo.TeacherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(private val teacherRepo: TeacherRepo,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val name: StateFlow<List<String>>
        get() = teacherRepo.name

    init {
        viewModelScope.launch(Dispatchers.IO) {
            teacherRepo.getName()
        }
    }

    val teacherName = savedStateHandle.get<String>("name") ?: ""

}
