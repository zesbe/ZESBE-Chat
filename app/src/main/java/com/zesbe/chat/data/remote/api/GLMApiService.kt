package com.zesbe.chat.data.remote.api

import com.zesbe.chat.BuildConfig
import com.zesbe.chat.data.remote.dto.ChatRequest
import com.zesbe.chat.data.remote.dto.ChatResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

/**
 * GLM API Service
 *
 * Retrofit interface for GLM API calls.
 */
interface GLMApiService {

    @POST("chat/completions")
    suspend fun chat(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): ChatResponse

    companion object {
        private const val BASE_URL = "https://open.bigmodel.cn/api/paas/v4/"

        fun create(): GLMApiService {
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method, original.body)
                        .build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GLMApiService::class.java)
        }
    }
}

/**
 * GLM Streaming API
 *
 * For SSE streaming responses using OkHttp EventSource.
 */
class GLMStreamingApi {

    companion object {
        private const val BASE_URL = "https://open.bigmodel.cn/api/paas/v4/"
    }

    fun streamChat(
        apiKey: String,
        request: ChatRequest,
        listener: EventSourceListener
    ): EventSource {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS) // No timeout for streaming
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val body = Buffer().writeJson(request)

        val httpRequest = Request.Builder()
            .url("${BASE_URL}chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(body)
            .build()

        return EventSources.createFactory(client)
            .newEventSource(httpRequest, listener)
    }

    private fun Buffer.writeJson(request: ChatRequest): Buffer {
        val json = com.google.gson.Gson().toJson(request)
        write(json.toByteArray())
        return this
    }
}
