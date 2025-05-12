package com.example.bookbuddy.data.repoimpl

import com.example.bookbuddy.common.BookCategoryModel
import com.example.bookbuddy.common.BookModel
import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.domain.repo.AllBookRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AllBookRepoImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) : AllBookRepo {
    override fun getAllBooks(): Flow<ResultState<List<BookModel>>> = callbackFlow {
        /*
        The `callbackFlow` creates a Kotlin Flow that bridges callback-based APIs (like Firebase's `ValueEventListener`)
        with coroutines. The callback (`ValueEventListener`) listens for changes in the Firebase Realtime Database.
        When data changes, `onDataChange` is called and emits the new data into the Flow using `trySend`.
        If there's an error, `onCancelled` emits an error state. This allows you to collect database updates reactively using coroutines.
        */
        trySend(ResultState.Loading) /*
        The selected line sends a `ResultState.Loading` value into the `callbackFlow`.
        This notifies any collectors of the flow that a loading state has started,
        typically used to show a loading indicator in the UI while data is being fetched from Firebase.
        */

        //THIS IS THE WAY TO GET FROM REALTIME DATABASE
        
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookModel> = emptyList()
                items = snapshot.children.map { 
                    valueSnapshot ->
                    valueSnapshot.getValue<BookModel>()!!
                }
                /*
                This code initializes a list of `BookModel` objects from a Firebase `DataSnapshot`.
                It iterates over each child in the snapshot, converts each child to a `BookModel` using
                `getValue<BookModel>()!!`, and collects them into a list. The `!!` operator assumes that
                 the conversion will never return null, so if any child cannot be converted, it will throw a `NullPointerException`.
                 */

                //AFTER GETTING THE DATA SUCCESSFULLY WE HAVE TO TELL THE STATE
                trySend(ResultState.Success(items)) /*
                The selected line sends a `ResultState.Success(items)` value into the `callbackFlow`.
                This emits the successfully retrieved list of `BookModel` objects (`items`) to any collectors of the flow,
                indicating that the data was loaded successfully from Firebase. This is typically used to update the UI with the new data.*/
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }

        /*The below line adds a `ValueEventListener` to the `Books` node in Firebase Realtime Database.
           This listener will receive updates whenever the data at `Books` changes, triggering
         the `onDataChange` or `onCancelled` callbacks to handle data retrieval or errors.*/
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)




        /*
        The `awaitClose` block in your `callbackFlow` suspends until the flow is closed or cancelled.
        When that happens, the code inside the block runs. Here, it removes the `ValueEventListener`
        from Firebase to prevent memory leaks and closes the flow. This ensures proper cleanup when the flow collection stops.
         */
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
        
    }

    override fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        /*
        The `valueEvent` variable holds an anonymous implementation of the `ValueEventListener` interface.
        It is needed so you can:
        - Pass it to `addValueEventListener()` to start listening for data changes in Firebase.
        - Reference the same listener instance later in `removeEventListener()` for proper cleanup in `awaitClose`, preventing memory leaks.
        - Define how to handle data updates (`onDataChange`) and errors (`onCancelled`) in one place.
         */
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookCategoryModel> = emptyList()
                items = snapshot.children.map { 
                    valueSnapshot ->
                    valueSnapshot.getValue<BookCategoryModel>()!!
                }

                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }

        firebaseDatabase.reference.child("BooksCategory").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>> = callbackFlow {
        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<BookModel> = emptyList()

                /*
                The selected code filters the children of the `DataSnapshot` to include only those where
                the `category` property of the deserialized `BookModel` matches the given `category` parameter.
                It uses `getValue<BookModel>()!!` to convert each child snapshot to a `BookModel` and
                compares its `category` field. Only matching items are kept for further processing.
                If any child cannot be converted to `BookModel`, a `NullPointerException` will be thrown due to the `!!` operator.
                 */
                items = snapshot.children.filter { 
                    valueSnapshot ->
                    valueSnapshot.getValue<BookModel>()!!.category == category
                }.map { 
                    valueSnapshot ->
                    valueSnapshot.getValue<BookModel>()!!
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
        }
    }


}




























