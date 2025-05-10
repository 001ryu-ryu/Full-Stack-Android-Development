package com.example.mathsirs.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mathsirs.repo.TeacherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectionViewModel @Inject constructor(private val teacherRepo: TeacherRepo,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {
        // call this before navigation
        fun selectTeacher(name: String, profilePic: String) {
            savedStateHandle["name"] = name
            savedStateHandle["profilePicture"] = profilePic
        }

    // getter for detail screen
    val teacherName: String
        get() = savedStateHandle.get<String>("name") ?: ""

    val teacherProfilePicture: String
        get() = savedStateHandle.get<String>("profilePicture") ?: ""
}