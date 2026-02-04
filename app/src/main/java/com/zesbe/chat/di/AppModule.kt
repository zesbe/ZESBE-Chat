package com.zesbe.chat.di

import android.content.Context
import androidx.room.Room
import com.zesbe.chat.BuildConfig
import com.zesbe.chat.data.local.dao.ChatDao
import com.zesbe.chat.data.local.database.ChatDatabase
import com.zesbe.chat.data.remote.api.GLMApiService
import com.zesbe.chat.data.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency Injection Module
 *
 * Provides dependencies for the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideChatDatabase(
        @ApplicationContext context: Context
    ): ChatDatabase {
        return Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            "chat_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideChatDao(database: ChatDatabase): ChatDao = database.chatDao()

    @Provides
    @Singleton
    fun provideGLMApiService(): GLMApiService = GLMApiService.create()

    @Provides
    @Singleton
    fun provideChatRepository(
        chatDao: ChatDao,
        apiService: GLMApiService
    ): ChatRepository = ChatRepository(chatDao, apiService)

    @Provides
    fun provideApiKey(): String = BuildConfig.GLM_API_KEY
}
