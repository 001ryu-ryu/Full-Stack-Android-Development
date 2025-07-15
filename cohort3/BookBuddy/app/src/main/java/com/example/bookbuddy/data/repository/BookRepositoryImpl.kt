package com.example.bookbuddy.data.repository

import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.data.model.Book
import com.example.bookbuddy.domain.repository.BookRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val firebaseDatabase: FirebaseDatabase) : BookRepository {

    override fun getAllTasks(): Flow<ResultState<List<Book>>> = callbackFlow {

        trySend(ResultState.Loading)

        val bookRef = firebaseDatabase.reference.child("Books")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val books = snapshot.children.mapNotNull {
                    it.getValue(Book::class.java)
                }
                trySend(ResultState.Success(books))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }
        bookRef.addValueEventListener(listener)

        awaitClose {
            bookRef.removeEventListener(listener)
            close()
        }
    }
}