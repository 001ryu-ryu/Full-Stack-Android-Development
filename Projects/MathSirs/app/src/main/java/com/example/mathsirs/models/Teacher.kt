package com.example.mathsirs.models

data class Teacher(
    val areaOfInterest: String,
    val degrees: List<String>,
    val name: String,
    val profilePicture: String,
    val role: String,
    val specializationOfSubjects: List<String>
)