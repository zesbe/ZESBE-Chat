package com.zesbe.chat.domain.model

/**
 * Message Domain Model
 *
 * Represents a message in the chat.
 */
data class Message(
    val id: String,
    val chatId: String,
    val content: String,
    val isUser: Boolean,
    val timestamp: Long,
    val isStreaming: Boolean = false
)

/**
 * Chat Domain Model
 *
 * Represents a conversation.
 */
data class Chat(
    val id: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long,
    val lastMessage: String = ""
)
