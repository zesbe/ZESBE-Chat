# ZESBE Chat - Android App

> **Modern AI Chat App with GLM Integration**
> Built with Kotlin + Jetpack Compose

---

## 🎯 Project Overview

**Name:** ZESBE Chat
**Type:** Android Application
**Language:** Kotlin
**UI Framework:** Jetpack Compose
**Architecture:** Clean Architecture + MVVM
**AI Provider:** ZhipuAI (GLM-4)

---

## 📁 Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/zesbe/chat/
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   │   ├── dao/
│   │   │   │   │   │   └── ChatDao.kt
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   └── MessageEntity.kt
│   │   │   │   │   └── database/
│   │   │   │   │       └── ChatDatabase.kt
│   │   │   │   ├── remote/
│   │   │   │   │   ├── api/
│   │   │   │   │   │   └── GLMApiService.kt
│   │   │   │   │   ├── dto/
│   │   │   │   │   │   └── ChatRequest.kt
│   │   │   │   │   └── repository/
│   │   │   │   │       └── ChatRepository.kt
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   │   └── Message.kt
│   │   │   │   └── usecase/
│   │   │   │       └── SendMessageUseCase.kt
│   │   │   ├── presentation/
│   │   │   │   ├── ui/
│   │   │   │   │   ├── chat/
│   │   │   │   │   │   ├── ChatScreen.kt
│   │   │   │   │   │   └── ChatViewModel.kt
│   │   │   │   │   ├── chatlist/
│   │   │   │   │   │   ├── ChatListScreen.kt
│   │   │   │   │   │   └── ChatListViewModel.kt
│   │   │   │   │   ├── settings/
│   │   │   │   │   │   ├── SettingsScreen.kt
│   │   │   │   │   │   └── SettingsViewModel.kt
│   │   │   │   │   └── theme/
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       └── Type.kt
│   │   │   │   └── navigation/
│   │   │   │       └── NavGraph.kt
│   │   │   ├── di/
│   │   │   │   └── AppModule.kt
│   │   │   └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts (project)
└── settings.gradle.kts
```

---

## 🎨 Features

### Core Features
- ✅ **Chat with AI** - GLM-4 integration
- ✅ **Chat History** - Local storage with Room
- ✅ **Multiple Chats** - Create/manage conversations
- ✅ **Markdown Support** - Rich text rendering
- ✅ **Code Highlighting** - Syntax highlighting for code
- ✅ **Dark Mode** - System theme support

### Advanced Features
- ✅ **Export Chat** - PDF/Text export
- ✅ **Settings** - API key, model selection
- ✅ **Search Messages** - Find in chat
- ✅ **Delete Chats** - Manage conversations
- ✅ **Stream Response** - Real-time AI response
- ✅ **Error Handling** - Graceful failure

---

## 🔧 Tech Stack

### Core
- **Kotlin** - 1.9.0+
- **Jetpack Compose** - 1.5.0+
- **Coroutines** - Async operations
- **Flow** - Reactive streams

### Architecture
- **MVVM** - Model-View-ViewModel
- **Clean Architecture** - Separation of concerns
- **Repository Pattern** - Data abstraction
- **Use Cases** - Business logic

### Data
- **Room Database** - Local storage
- **Retrofit** - API client
- **DataStore** - Settings storage
- **Gson/JSON** - Serialization

### UI
- **Jetpack Compose** - Modern UI
- **Material 3** - Design system
- **Navigation Compose** - Screen navigation
- **Coil** - Image loading

### Libraries
- **Markwon** - Markdown rendering
- **Prism4J** - Code highlighting
- **ItextPdf** - PDF export
- **Kamel** - Image loading in Compose

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17 or higher
- Android SDK API 24+ (Android 7.0)
- ZhipuAI API Key

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/zesbe/ZESBE-Chat.git
cd ZESBE-Chat
```

2. **Open in Android Studio**
   - File → Open
   - Select ZESBE-Chat directory
   - Wait for Gradle sync

3. **Get API Key**
   - Visit: https://open.bigmodel.cn/
   - Sign up/Login
   - Get your API key
   - Add to `local.properties`:
     ```properties
     glm.api.key=your_api_key_here
     ```

4. **Run the app**
   - Connect device or emulator
   - Click Run (▶️) or press Shift+F10

---

## 📱 Screens

### 1. Chat List Screen
- List of all conversations
- Create new chat
- Delete conversations
- Search chats
- Last message preview

### 2. Chat Screen
- Message list (user + AI)
- Message input field
- Send button
- Loading indicator
- Error handling
- Export option

### 3. Settings Screen
- API key configuration
- Model selection (GLM-4, GLM-3-Turbo)
- Theme selection
- Clear chat history
- About/Version info

---

## 🔐 Security

### API Key Storage
- **Development:** `local.properties` (not committed)
- **Production:** DataStore (encrypted)

### Best Practices
- ✅ Never commit API keys
- ✅ Use encrypted storage
- ✅ Validate API keys
- ✅ Handle key expiration
- ✅ Rotate keys periodically

---

## 🎨 Design

### Colors
- Primary: Electric Blue (#00F0FF)
- Background: Dark (#0A0A0A) / Light (#FFFFFF)
- Surface: Slightly lighter/darker
- On Surface: White (#FFFFFF) / Black (#000000)

### Typography
- Headings: Space Grotesk Bold
- Body: Inter Regular
- Code: JetBrains Mono

### Components
- **MessageBubble** - Chat message
- **MessageInput** - Text field + send
- **ChatItem** - List item
- **SettingsItem** - Setting row
- **LoadingIndicator** - Spinner

---

## 📊 Database Schema

### Messages Table
```sql
CREATE TABLE messages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    chat_id TEXT NOT NULL,
    content TEXT NOT NULL,
    is_user BOOLEAN NOT NULL,
    timestamp INTEGER NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);
```

### Chats Table
```sql
CREATE TABLE chats (
    id TEXT PRIMARY KEY,
    title TEXT NOT NULL,
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL
);
```

---

## 🌐 API Integration

### ZhipuAI (GLM) API

**Endpoint:** `https://open.bigmodel.cn/api/paas/v4/chat/completions`

**Request:**
```json
{
  "model": "glm-4",
  "messages": [
    {
      "role": "user",
      "content": "Hello!"
    }
  ],
  "stream": true
}
```

**Response (Streamed):**
```
data: {"id": "...", "choices": [{"delta": {"content": "Hi"}}]}
data: [DONE]
```

---

## 🧪 Testing

### Unit Tests
- ViewModel tests
- Repository tests
- Use case tests
- Utility tests

### UI Tests
- Compose UI tests
- Navigation tests
- User flow tests

### Integration Tests
- API tests
- Database tests
- End-to-end tests

---

## 📝 TODO

### Phase 1 - MVP ✅
- [x] Basic chat functionality
- [x] GLM integration
- [x] Chat history
- [x] Settings screen

### Phase 2 - Enhancement
- [ ] Streaming response
- [ ] Markdown rendering
- [ ] Code highlighting
- [ ] Export chat

### Phase 3 - Advanced
- [ ] Multiple AI support
- [ ] Voice input
- [ ] Image upload
- [ ] Custom instructions

---

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

---

## 📄 License

```
MIT License

Copyright (c) 2026 ZESBE

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

---

## 👨‍💻 Author

**ZESBE**
- Website: zesbe.com
- GitHub: @zesbe
- Twitter: @zesbe

---

## 🙏 Acknowledgments

- ZhipuAI for GLM API
- JetBrains for Kotlin
- Google for Jetpack Compose
- Open-source community

---

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Email: support@zesbe.com
- Twitter: @zesbe

---

**Build Different.** ⚡

*Made with ❤️ by ZESBE*
# ZESBE Chat
