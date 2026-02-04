package com.zesbe.chat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Chat Entity for Room Database
 *
 * Represents a conversation.
 */
@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long
)
