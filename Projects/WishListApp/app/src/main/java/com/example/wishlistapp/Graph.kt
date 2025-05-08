package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository

//To initialize the data base
object Graph { // In kotlin object keyword is used to declare a singleton(a class which only one instance will be exist in the whole app)
    lateinit var database: WishDatabase
    
    val wishRepository by lazy { //by lazy-> This variable is only initialize when it is needed
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context) { // Now this fun will initialize the data base, the context is needed to know to which context I want to build my database
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
        
    }
}