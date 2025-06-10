package com.example.roomdemo.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomdemo.database.Product
import com.example.roomdemo.database.ProductDatabase
import com.example.roomdemo.repository.ProductRepository

class MainViewModel(application: Application) : ViewModel() {
    val allProducts: LiveData<List<Product>>
    private val repository: ProductRepository
    val searchResults: MutableLiveData<List<Product>>

    init {
        val productDb = ProductDatabase.getInstance(application)
        val productDao = productDb.productDao()
        repository = ProductRepository(productDao)
        allProducts = repository.allProducts
        searchResults = repository.searchResults as MutableLiveData<List<Product>>
    }

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        repository.deleteProduct(name)
    }
}
























