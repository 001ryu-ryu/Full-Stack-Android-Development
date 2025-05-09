package com.example.mathsirs.repo

import com.example.mathsirs.api.TeacherApi
import com.example.mathsirs.models.TeacherDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TeacherRepo @Inject constructor(private val teacherApi: TeacherApi) {

    //for whole teacher
    private val _teacher = MutableStateFlow<List<TeacherDetails>>(emptyList())
    val teacher: StateFlow<List<TeacherDetails>>
        get() = _teacher
    suspend fun getTeacher(name: String) {
        val response = teacherApi.getTeacher("teachers[?(@.name==\"$name\")]")
        if (response.isSuccessful && response.body() != null) {
            _teacher.emit(response.body()!!)
        }
    }

    // for teacher profile picture
    private val _profilePicture = MutableStateFlow<List<String>>(emptyList())
    val profilePicture: StateFlow<List<String>>
        get() = _profilePicture
    suspend fun getProfilePicture() {
        val response = teacherApi.getProfilePicture()
        if (response.isSuccessful && response.body() != null) {
            _profilePicture.emit(response.body()!!)
        }
    }

    
    // for teacher name
    private val _name = MutableStateFlow<List<String>>(emptyList())
    val name: StateFlow<List<String>>
        get() = _name
    suspend fun getName() {
        val response = teacherApi.getName()
        if (response.isSuccessful && response.body() != null) {
            _name.emit(response.body()!!)
        }
    }

    // for teacher degree
   private val _degrees = MutableStateFlow<List<List<String>>>(emptyList())
    val degree: StateFlow<List<List<String>>>
        get() = _degrees
    suspend fun getDegrees() {
        val response = teacherApi.getDegrees()
        if (response.isSuccessful && response.body() != null) {
            _degrees.emit(response.body()!!)
        }
    }

    //for area of interest

    private val _areaOfInterest = MutableStateFlow<List<String>>(emptyList())
    val areaOfInterest: StateFlow<List<String>>
        get() = _areaOfInterest
    suspend fun getAreaOfInterest() {
        val response = teacherApi.getAreaOfInterest()
        if (response.isSuccessful && response.body() != null) {
            _areaOfInterest.emit(response.body()!!)
        }
    }


    //For teacher role
    private val _role = MutableStateFlow<List<String>>(emptyList())
    val role: StateFlow<List<String>> 
        get() = _role
    suspend fun getRole() {
        val response = teacherApi.getRole()
        if (response.isSuccessful && response.body() != null) {
            _role.emit(response.body()!!)
        }
    }

    // For teacher specialization of subjects
    private val _specializationOfSubjects = MutableStateFlow<List<List<String>>>(emptyList())
    val specializationOfSubjects: StateFlow<List<List<String>>>
        get() = _specializationOfSubjects
    
    
    
}