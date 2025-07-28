package com.example.chatter.feature.chat

import androidx.lifecycle.ViewModel
import com.example.chatter.model.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val _messages by lazy { MutableStateFlow<List<Message>>(emptyList()) }
    val messages = _messages.asStateFlow()

    val db = Firebase.database

    fun sendMessage(channelId: String, messageText: String) {
        val message = Message(
            id = db.reference.push().key ?: UUID.randomUUID().toString(),
            text = messageText,
            timestamp = System.currentTimeMillis(),
            senderId = Firebase.auth.currentUser?.uid ?: "",
            senderName = Firebase.auth.currentUser?.displayName ?: "",
            senderImage = null,
            imageUrl = null
        )
        db.getReference("messages").child(channelId).push().setValue(message)
    }

    fun getMessages(channelId: String) {
        db.getReference("messages").child(channelId).orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = snapshot.children.mapNotNull {
                        it.getValue(Message::class.java)
                    }
                    _messages.value = messages

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            )
    }
}