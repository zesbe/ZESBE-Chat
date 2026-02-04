package com.zesbe.chat.presentation.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zesbe.chat.data.local.entity.MessageEntity
import com.zesbe.chat.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okio.Buffer
import org.json.JSONObject
import java.util.UUID
import javax.inject.Inject

/**
 * Chat ViewModel
 *
 * Manages chat screen state and business logic.
 */
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Initial)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<MessageEntity>>(emptyList())
    val messages: StateFlow<List<MessageEntity>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var currentEventSource: EventSource? = null

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            repository.getMessagesForChat(chatId).collect { messageList ->
                _messages.value = messageList
            }
        }
    }

    fun sendMessage(
        chatId: String,
        content: String,
        apiKey: String,
        model: String = "glm-4"
    ) {
        if (content.isBlank()) return

        viewModelScope.launch {
            // Create user message
            val userMessage = MessageEntity(
                id = UUID.randomUUID().toString(),
                chatId = chatId,
                content = content,
                isUser = true,
                timestamp = System.currentTimeMillis()
            )

            repository.insertMessage(userMessage)

            // Create placeholder for AI response
            val aiMessage = MessageEntity(
                id = UUID.randomUUID().toString(),
                chatId = chatId,
                content = "",
                isUser = false,
                timestamp = System.currentTimeMillis(),
                isStreaming = true
            )

            repository.insertMessage(aiMessage)
            _isLoading.value = true

            // Get all messages for API
            val allMessages = _messages.value + userMessage

            // Send to API with streaming
            val streamListener = object : EventSourceListener() {
                private val responseBuffer = StringBuilder()

                override fun onOpen(eventSource: EventSource, response: okhttp3.Response) {
                    _uiState.value = ChatUiState.Ready
                }

                override fun onEvent(
                    eventSource: EventSource,
                    id: String?,
                    type: String?,
                    data: String
                ) {
                    if (data == "[DONE]") return

                    try {
                        val json = JSONObject(data)
                        val content = json
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("delta")
                            .optString("content", "")

                        if (content.isNotEmpty()) {
                            responseBuffer.append(content)

                            // Update AI message with streaming content
                            viewModelScope.launch {
                                repository.updateMessage(
                                    aiMessage.copy(content = responseBuffer.toString())
                                )
                            }
                        }
                    } catch (e: Exception) {
                        // Parse error, ignore
                    }
                }

                override fun onClosed(eventSource: EventSource) {
                    viewModelScope.launch {
                        repository.updateMessage(
                            aiMessage.copy(
                                content = responseBuffer.toString(),
                                isStreaming = false
                            )
                        )
                        _isLoading.value = false
                    }
                }

                override fun onFailure(
                    eventSource: EventSource,
                    t: Throwable?,
                    response: okhttp3.Response?
                ) {
                    _uiState.value = ChatUiState.Error(t?.message ?: "Unknown error")
                    _isLoading.value = false

                    // Remove placeholder message
                    viewModelScope.launch {
                        repository.updateMessage(
                            aiMessage.copy(
                                content = "Error: ${t?.message}",
                                isStreaming = false
                            )
                        )
                    }
                }
            }

            try {
                currentEventSource = repository.sendMessage(
                    apiKey = apiKey,
                    chatId = chatId,
                    messages = allMessages,
                    model = model,
                    streamListener = streamListener
                )
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error(e.message ?: "Unknown error")
                _isLoading.value = false
            }
        }
    }

    fun stopStreaming() {
        currentEventSource?.cancel()
        currentEventSource = null
        _isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        stopStreaming()
    }
}

sealed class ChatUiState {
    object Initial : ChatUiState()
    object Ready : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}
