package com.example.mathsirs.api

import com.example.mathsirs.models.TeacherDetails
import com.example.mathsirs.utils.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TeacherApi {
    @GET(ENDPOINT)
    suspend fun getTeacher(@Header("X-JSON-Path") name: String): Response<List<TeacherDetails>>
    @GET(ENDPOINT)
    @Headers("X-JSON-Path: teachers..name")
    suspend fun getName(): Response<List<String>>
    
    @GET(ENDPOINT)
    @Headers("X-JSON-Path: teachers..profile_picture")
    suspend fun getProfilePicture(): Response<List<String>>
    
    @GET(ENDPOINT)
    @Headers("X-JSON-Path: teachers..degrees")
    suspend fun getDegrees(): Response<List<List<String>>>
    @GET(ENDPOINT)
    @Headers("X-JSON-Path: teachers..area_of_interest")
    suspend fun getAreaOfInterest(): Response<List<String>>
    
    @GET(ENDPOINT)
    @Headers("X-JSON-Path: teachers..role")
    suspend fun getRole(): Response<List<String>>
    
    @GET(ENDPOINT)
    @Headers("X-JSON-Path: teachers..specialization_of_subjects")
    suspend fun getSpecializationOfSubjects(): Response<List<List<String>>>
    

}