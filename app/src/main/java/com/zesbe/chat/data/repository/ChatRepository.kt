package com.zesbe.chat.data.repository

import com.zesbe.chat.data.local.dao.ChatDao
import com.zesbe.chat.data.local.entity.ChatEntity
import com.zesbe.chat.data.local.entity.MessageEntity
import com.zesbe.chat.data.remote.api.GLMApiService
import com.zesbe.chat.data.remote.dto.ChatRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Chat Repository
 *
 * Single source of truth for chat data.
 * Combines local database and remote API.
 */
@Singleton
class ChatRepository @Inject constructor(
    private val chatDao: ChatDao,
    private val apiService: GLMApiService
) {

    // Chat operations
    fun getAllChats(): Flow<List<ChatEntity>> = chatDao.getAllChats()

    suspend fun getChatById(chatId: String): ChatEntity? = chatDao.getChatById(chatId)

    suspend fun createChat(title: String): ChatEntity {
        val chat = ChatEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        chatDao.insertChat(chat)
        return chat
    }

    suspend fun updateChatTitle(chatId: String, title: String) {
        chatDao.getChatById(chatId)?.let { chat ->
            chatDao.updateChat(chat.copy(title = title, updatedAt = System.currentTimeMillis()))
        }
    }

    suspend fun deleteChat(chatId: String) {
        chatDao.deleteMessagesForChat(chatId)
        chatDao.deleteChat(chatId)
    }

    suspend fun deleteAllChats() {
        chatDao.deleteAllMessages()
        chatDao.deleteAllChats()
    }

    // Message operations
    fun getMessagesForChat(chatId: String): Flow<List<MessageEntity>> =
        chatDao.getMessagesForChat(chatId)

    suspend fun insertMessage(message: MessageEntity) {
        chatDao.insertMessage(message)

        // Update chat timestamp
        chatDao.getChatById(message.chatId)?.let { chat ->
            chatDao.updateChat(chat.copy(updatedAt = System.currentTimeMillis()))
        }
    }

    suspend fun updateMessage(message: MessageEntity) = chatDao.updateMessage(message)

    // API operations
    suspend fun sendMessage(
        apiKey: String,
        chatId: String,
        messages: List<MessageEntity>,
        model: String = "glm-4",
        streamListener: EventSourceListener
    ): EventSource {
        // Convert local messages to API format
        val apiMessages = messages.map { message ->
            ChatRequest.Message(
                role = if (message.isUser) "user" else "assistant",
                content = message.content
            )
        }

        val request = ChatRequest(
            model = model,
            messages = apiMessages,
            stream = true
        )

        return com.zesbe.chat.data.remote.api.GLMStreamingApi().streamChat(
            apiKey = apiKey,
            request = request,
            listener = streamListener
        )
    }

    suspend fun sendMessageNonStream(
        apiKey: String,
        messages: List<MessageEntity>,
        model: String = "glm-4"
    ): String {
        val apiMessages = messages.map { message ->
            ChatRequest.Message(
                role = if (message.isUser) "user" else "assistant",
                content = message.content
            )
        }

        val request = ChatRequest(
            model = model,
            messages = apiMessages,
            stream = false
        )

        val response = apiService.chat(
            authorization = "Bearer $apiKey",
            request = request
        )

        return response.choices.firstOrNull()?.message?.content ?: ""
    }
}
