package com.example.mathsirs.di

import com.example.mathsirs.api.TeacherApi
import com.example.mathsirs.repo.TeacherRepo
import com.example.mathsirs.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {
    
    //to get retrofit object
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    // to get api object
    @Singleton
    @Provides
    fun providesTeacherApi(retrofit: Retrofit): TeacherApi {
        return retrofit.create(TeacherApi::class.java) // here we will have to pass the interface
    }

    // these two methods will help to to create object corresponding the interface

    @Singleton
    @Provides
    fun provideTeacherRepo(teacherApi: TeacherApi): TeacherRepo {
        return TeacherRepo(teacherApi)
    }
}