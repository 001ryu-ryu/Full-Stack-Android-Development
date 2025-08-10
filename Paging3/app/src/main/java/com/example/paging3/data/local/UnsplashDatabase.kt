package com.example.paging3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paging3.data.local.dao.UnsplashImageDao
import com.example.paging3.data.local.dao.UnsplashRemoteKeysDao
import com.example.paging3.model.UnsplashImage
import com.example.paging3.model.UnsplashRemoteKey

@Database(entities = [UnsplashImage::class, UnsplashRemoteKey::class], version = 1)
abstract class UnsplashDatabase : RoomDatabase(){
    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao
}