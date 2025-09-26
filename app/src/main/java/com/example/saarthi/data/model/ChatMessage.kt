package com.example.saarthi.data.model

enum class Author {
    USER, BOT
}

data class ChatMessage(
    val text: String,
    val author: Author
)