package org.example.room_contactapp.di.modules

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.room_contactapp.data.ContactRepositoryImpl
import org.example.room_contactapp.data.database.ContactDatabase
import org.example.room_contactapp.data.database.DatabaseFactory
import org.example.room_contactapp.presentation.MainViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single {
        get<DatabaseFactory>().create().setDriver(BundledSQLiteDriver()).build()
    }

    single {
        get<ContactDatabase>().contactDao
    }

    singleOf(::ContactRepositoryImpl).bind<ContactDatabase>()
    viewModelOf(::MainViewModel)
}