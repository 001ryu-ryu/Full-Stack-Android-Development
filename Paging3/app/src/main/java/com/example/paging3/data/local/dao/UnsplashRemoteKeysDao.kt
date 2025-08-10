package com.example.paging3.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paging3.model.UnsplashRemoteKey

@Dao
interface UnsplashRemoteKeysDao {
    @Query("SELECT * FROM unsplash_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<UnsplashRemoteKey>)

    @Query("DELETE FROM unsplash_remote_keys_table")
    suspend fun deleteAllKeys()
}