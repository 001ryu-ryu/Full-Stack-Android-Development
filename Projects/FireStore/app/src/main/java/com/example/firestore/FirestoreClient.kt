package com.example.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

//in data layer in clean code
class FirestoreClient {
    private val tag = "FirestoreClient: "
    private val db = FirebaseFirestore.getInstance()
    private val collection = "users"

    fun upsertUser(
        user: User
    ): Flow<Boolean> {
        return callbackFlow {
            db.collection(collection)
                .document(user.id)
                .set(user.toHashMap(), SetOptions.merge())
                .addOnSuccessListener {
                    println(tag + "Insert with id: ${user.id} ")
                    trySend(true)
                }
                .addOnFailureListener {
                    println(tag + "error: ${it.message} ")
                    trySend(false)
                }
            awaitClose {}
        }
    }

    fun getUser(email: String): Flow<User?> {
        return callbackFlow {
            db.collection(collection)
                .get()
                .addOnSuccessListener { result->
                    var user: User? = null
                    result.forEach {
                        if (it.data["email"] == email) {
                            user = it.data.toUser()
                            println(tag + "user is $user ")
                            trySend(user)
                        }
                    }

                    if (user == null) {
                        println(tag + "user not found for $email")
                        trySend(null)
                    }

                }
                .addOnFailureListener {
                    trySend(null)
                }
            awaitClose {  }
        }
    }


    private fun User.toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "age" to age
        )
    }

    private fun Map<String, Any>.toUser(): User {
        return User(
            id = this["id"] as String,
            name = this["name"] as String,
            email = this["email"] as String,
            age = (this["age"] as Long).toInt()
        )
    }
}