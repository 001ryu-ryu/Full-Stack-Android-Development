package com.example.chatgptclone.repo

import android.util.Log
import com.example.chatgptclone.API_KEY
import com.example.chatgptclone.BASE_URL
import com.example.chatgptclone.CHAT_GPT_MODEL
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class ChatRepo @Inject constructor(private val okHttpClient: OkHttpClient){

    fun streamChatCompletionWithHistory(messageHistory: List<Pair<String, String>>): Flow<String> = callbackFlow {
        val url = "${BASE_URL}chat/completions"
        val json = JSONObject().apply {
            put("model", CHAT_GPT_MODEL)
            put("stream", true)
            put("messages", JSONArray().apply {
                messageHistory.forEach { (role, content) ->
                    put(JSONObject().apply {
                        put("role", role)
                        put("content", content)
                    })
                }
            })
        }
        val body = json.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        val call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ChatRepo", "onFailure: ${e.message}")
                trySend("[error]: ${e::class.simpleName} - ${e.message ?: "No message"}")
                close(e)
            }
            override fun onResponse(call: Call, response: Response) {
                Log.d("ChatRepo", "onResponse: HTTP ${response.code}")
                if (!response.isSuccessful) {
                    Log.e("ChatRepo", "HTTP error: ${response.code} - ${response.message}")
                    trySend("[error]: HTTP ${response.code} - ${response.message}")
                    close(IOException("HTTP error: ${response.code} - ${response.message}"))
                    return
                }
                val source = response.body?.source() ?: run {
                    Log.e("ChatRepo", "Null response body")
                    trySend("[error]: Null response body")
                    close(IOException("Null response body"))
                    return
                }
                try {
                    while (!source.exhausted()) {
                        val line = source.readUtf8Line()
                        Log.d("ChatRepo", "Read line: $line")
                        if (line != null && line.startsWith("data: ")) {
                            val data = line.removePrefix("data: ").trim()
                            Log.d("ChatRepo", "Data chunk: $data")
                            if (data == "[DONE]") break
                            try {
                                val obj = JSONObject(data)
                                val content = obj
                                    .optJSONArray("choices")
                                    ?.optJSONObject(0)
                                    ?.optJSONObject("delta")
                                    ?.optString("content")
                                Log.d("ChatRepo", "Parsed content: $content")
                                if (!content.isNullOrEmpty()) {
                                    trySend(content)
                                    Log.d("ChatRepo", "Sent chunk: $content")
                                }
                            } catch (e: Exception) {
                                Log.e("ChatRepo", "Malformed line: $data, error: ${e.message}")
                                trySend("[error]: Malformed line: $data, error: ${e.message}")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("ChatRepo", "Exception: ${e.message}")
                    trySend("[error]: ${e::class.simpleName} - ${e.message ?: "No message"}")
                } finally {
                    response.close()
                    close()
                }
            }
        })
        awaitClose { call.cancel() }
    }
}
