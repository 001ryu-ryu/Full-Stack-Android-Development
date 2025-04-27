package org.example.room_contactapp.di.modules

import org.example.room_contactapp.data.database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single {
            DatabaseFactory()
        }
    }