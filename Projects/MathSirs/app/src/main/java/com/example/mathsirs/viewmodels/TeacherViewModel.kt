package com.example.mathsirs.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathsirs.models.TeacherDetails
import com.example.mathsirs.repo.TeacherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(private val teacherRepo: TeacherRepo,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val teacher: StateFlow<List<TeacherDetails>>
        get() = teacherRepo.teacher

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val name = savedStateHandle.get<String>("name") ?: ""
            teacherRepo.getTeacher("name")
        }
    }
}