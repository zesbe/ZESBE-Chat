package com.zesbe.chat.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zesbe.chat.data.local.dao.ChatDao
import com.zesbe.chat.data.local.entity.ChatEntity
import com.zesbe.chat.data.local.entity.MessageEntity

/**
 * Chat Database
 *
 * Room database for local chat storage.
 */
@Database(
    entities = [ChatEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao
}
