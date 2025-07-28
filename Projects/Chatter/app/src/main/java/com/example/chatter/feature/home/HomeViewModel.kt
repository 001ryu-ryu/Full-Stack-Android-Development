package com.example.chatter.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.chatter.model.Channel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {
    val firebaseDatabase = Firebase.database

    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    init {
        getChannels()
    }

    fun getChannels() {
        firebaseDatabase.getReference("channels").get()
            .addOnSuccessListener { dataSnapshot ->
                val channels = dataSnapshot.children.mapNotNull {
                    it.getValue(Channel::class.java)
                }
                Log.i("View data", channels.toString())
                _channels.value = channels
            }
            .addOnFailureListener {
                // Handle error
            }

    }

    fun addChannel(name: String) {
        val channel = Channel(name = name)
        firebaseDatabase.getReference("channels").push().setValue(channel)

    }
}