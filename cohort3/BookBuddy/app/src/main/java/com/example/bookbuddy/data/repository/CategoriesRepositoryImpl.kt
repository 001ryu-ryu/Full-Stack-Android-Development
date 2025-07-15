package com.example.bookbuddy.data.repository

import android.util.Log
import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.data.model.BookCategory
import com.example.bookbuddy.domain.repository.CategoriesRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val firebaseDatabase: FirebaseDatabase) :
    CategoriesRepository {
    override fun getBooksCategories(): Flow<ResultState<List<BookCategory>>> = callbackFlow {
        trySend(ResultState.Loading)

        val catRef = firebaseDatabase.reference.child("BookCategory")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories = snapshot.children.mapNotNull {
                    it.getValue(BookCategory::class.java)
                }
                trySend(ResultState.Success(categories))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }
        }

        catRef.addValueEventListener(listener)

        awaitClose {
            catRef.removeEventListener(listener)
            close()
        }
    }
}


























