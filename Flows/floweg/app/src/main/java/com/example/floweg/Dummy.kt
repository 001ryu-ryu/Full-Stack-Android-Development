package com.example.floweg

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow


fun getNotes(): Flow<Note> {
    val list = listOf(
        Note(1, true, "First", "First Description"),
        Note(2, false, "Second", "Scond Description"),
        Note(3, true, "Third", "Third Description")
    )
    return list.asFlow()
}






data class Note(val id: Int, val isActive: Boolean, val title: String, val description: String)
data class FormatedNote(val isActive: Boolean, val title: String, val description: String)