package com.example.glancewidget.glance

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

object PriceDataRepo {
    var ticket = "Google"
    private var previousPrice = 0f
    var change = 0

    private var _currentPrice = MutableStateFlow(0f)
    val currentPrice = _currentPrice.asStateFlow()

    fun update() {
        previousPrice = currentPrice.value
        _currentPrice.value = Random.nextInt(20, 35) + Random.nextFloat()
        change = ((_currentPrice.value - previousPrice) / previousPrice * 100).toInt()
    }
}