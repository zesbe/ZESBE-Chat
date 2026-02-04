package com.zesbe.chat.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * GLM Chat Request
 *
 * Request body for GLM API.
 */
data class ChatRequest(
    @SerializedName("model")
    val model: String,

    @SerializedName("messages")
    val messages: List<Message>,

    @SerializedName("stream")
    val stream: Boolean = true,

    @SerializedName("temperature")
    val temperature: Float = 0.7f,

    @SerializedName("top_p")
    val topP: Float = 0.9f,

    @SerializedName("max_tokens")
    val maxTokens: Int? = null
) {

    data class Message(
        @SerializedName("role")
        val role: String, // "user", "assistant", "system"

        @SerializedName("content")
        val content: String
    )
}

/**
 * GLM Chat Response
 *
 * Response from GLM API.
 */
data class ChatResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("object")
    val objectType: String,

    @SerializedName("created")
    val created: Long,

    @SerializedName("model")
    val model: String,

    @SerializedName("choices")
    val choices: List<Choice>,

    @SerializedName("usage")
    val usage: Usage?
) {

    data class Choice(
        @SerializedName("index")
        val index: Int,

        @SerializedName("message")
        val message: Message,

        @SerializedName("finish_reason")
        val finishReason: String?
    ) {

        data class Message(
            @SerializedName("role")
            val role: String,

            @SerializedName("content")
            val content: String
        )
    }

    data class Usage(
        @SerializedName("prompt_tokens")
        val promptTokens: Int,

        @SerializedName("completion_tokens")
        val completionTokens: Int,

        @SerializedName("total_tokens")
        val totalTokens: Int
    )
}

/**
 * Streaming Response
 *
 * For SSE (Server-Sent Events) streaming.
 */
data class StreamChunk(
    @SerializedName("id")
    val id: String,

    @SerializedName("object")
    val objectType: String,

    @SerializedName("created")
    val created: Long,

    @SerializedName("model")
    val model: String,

    @SerializedName("choices")
    val choices: List<StreamChoice>
) {

    data class StreamChoice(
        @SerializedName("index")
        val index: Int,

        @SerializedName("delta")
        val delta: Delta,

        @SerializedName("finish_reason")
        val finishReason: String?
    ) {

        data class Delta(
            @SerializedName("role")
            val role: String? = null,

            @SerializedName("content")
            val content: String? = null
        )
    }
}
