package com.zesbe.chat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Message Entity for Room Database
 *
 * Represents a single message in a conversation.
 */
@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: String,
    val chatId: String,
    val content: String,
    val isUser: Boolean,
    val timestamp: Long,
    val isStreaming: Boolean = false
)
