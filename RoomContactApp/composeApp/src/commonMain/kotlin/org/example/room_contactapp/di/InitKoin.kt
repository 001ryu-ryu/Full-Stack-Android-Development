package org.example.room_contactapp.di

import org.example.room_contactapp.di.modules.platformModule
import org.example.room_contactapp.di.modules.sharedModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            sharedModule
        )
    }
}