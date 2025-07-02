package com.example.contact.di.modules

import android.app.Application
import androidx.room.Room
import com.example.contact.model.database.ContactDao
import com.example.contact.model.database.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ContactDatabase {
        return Room.databaseBuilder(
            application,
            ContactDatabase::class.java,
            "contact_table"
        ).build()
    }

    @Provides
    @Singleton
    fun providesContactDao(contactDatabase: ContactDatabase): ContactDao {
        return contactDatabase.getDao()
    }
}