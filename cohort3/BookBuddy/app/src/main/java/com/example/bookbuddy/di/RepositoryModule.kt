package com.example.bookbuddy.di

import com.example.bookbuddy.data.repository.BookRepositoryImpl
import com.example.bookbuddy.data.repository.CategoriesRepositoryImpl
import com.example.bookbuddy.domain.repository.BookRepository
import com.example.bookbuddy.domain.repository.CategoriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideBookRepository(impl: BookRepositoryImpl): BookRepository

    @Singleton
    @Binds
    abstract fun provideCategoriesRepository(impl: CategoriesRepositoryImpl): CategoriesRepository
}