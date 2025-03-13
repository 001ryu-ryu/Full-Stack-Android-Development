package com.example.depinj

import android.util.Log
import javax.inject.Inject

class Coffee(var milk: Milk) {
    fun makeCoffee() {
        milk.giveMilk()
        Log.d("TAG", "Make coffee is performed")
    }
}

class Milk @Inject constructor(var coffeeMaker: CoffeeMaker) {
    fun giveMilk() {
        coffeeMaker.sugar()
        Log.d("TAG", "giveMilk is performed")
    }
}

class milkQuantity {
    fun milkLiter(): Int {
        return 6
    }
}

interface CoffeeMaker {
    fun sugar()
}

class CoffeeMakerIml(): CoffeeMaker {
    override fun sugar() {
        Log.d("TAG", "Sugar is performed")
    }

}