package com.example.chatgptclone.repo

import android.util.Log
import com.example.chatgptclone.API_KEY
import com.example.chatgptclone.BASE_URL
import com.example.chatgptclone.CHAT_GPT_MODEL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class ChatRepo @Inject constructor(private val okHttpClient: OkHttpClient){

    fun streamChatCompletion(message: String): Flow<String> = flow {
        Log.d("Chat-Repo", "Repo fun initiated")
            val url = "${BASE_URL}chat/completions"
            val json = JSONObject().apply {
                put("model", CHAT_GPT_MODEL)
                put("stream", true)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", "Your name is Ryu, an expert and a genius of Mathematics")
                    })
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", message)
                    })
                })
            }

            val body = json.toString()
                .toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $API_KEY")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build()

            try {
                okHttpClient.newCall(request).execute().use { response ->
                    Log.d("Chat-Repo", "HTTP response received: ${response.code}")
                    if (!response.isSuccessful) {
                        emit("[error]: HTTP ${response.code} - ${response.message}")
                        Log.e("Chat-Repo", "HTTP error: ${response.code} - ${response.message}")
                    }

                    val source = response.body?.source()
                        ?: throw IOException("Null response body")

                    var lineCount = 0
                    while (!source.exhausted()) {
                        val line = source.readUtf8Line()
                        Log.d("Chat-Repo", "Read line: $line")
                        if (line != null && line.startsWith("data: ")) {
                            val data = line.removePrefix("data: ").trim()
                            Log.d("Chat-Repo", "Data chunk: $data")
                            if (data == "[DONE]") break

                            try {
                                val obj = JSONObject(data)
                                val content = obj
                                    .optJSONArray("choices")
                                    ?.optJSONObject(0)
                                    ?.optJSONObject("delta")
                                    ?.optString("content")

                                if (!content.isNullOrEmpty()) {
                                    emit(content)
                                    Log.d("Chat-Repo", "Emitted content: $content")
                                }
                            } catch (e: Exception) {
                                Log.e("Chat-Repo", "Malformed line: $data, error: ${e.message}")
                            }
                        }
                        lineCount++
                    }
                    Log.d("Chat-Repo", "Total lines read: $lineCount")
                }
            } catch (e: Exception) {
                emit("[error]: ${e::class.simpleName} - ${e.message ?: "No message"}")
                Log.e("Chat-Repo", "Exception: ${e::class.simpleName} - ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
}
