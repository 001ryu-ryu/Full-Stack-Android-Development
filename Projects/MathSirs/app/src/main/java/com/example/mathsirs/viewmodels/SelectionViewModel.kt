package com.example.mathsirs.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mathsirs.repo.TeacherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectionViewModel @Inject constructor(
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
        get() {
            val pic = savedStateHandle.get<String>("profilePicture") ?: "pictest"
            Log.d("Selection", "Profile picture from SavedStateHandle: $pic")
            return pic
        }
}