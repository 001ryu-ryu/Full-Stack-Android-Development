package com.example.depinj

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/*To provide the dependencies, we need module and module is of type object*/
@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideCoffee(milk: Milk): Coffee {
        return Coffee(milk)
    }

    @Provides
    fun provideMilk(@Coffeemaker1 coffeeMaker: CoffeeMaker): Milk {
        return Milk(coffeeMaker)
    }

    @Provides
    fun provideMilkQUantity(): milkQuantity {
        return milkQuantity()
    }

    @Provides
    @Coffeemaker1
    fun provideCoffeeMaker1(): CoffeeMaker {
        return CoffeeMakerIml()
    }

    @Provides
    @Coffeemaker2
    fun provideCoffeeMaker2(): CoffeeMaker {
        return CoffeeMakerIml()
    }
}

//If we need to pass two implements, we will be needing QUALIFIERS
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Coffeemaker1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Coffeemaker2