package com.example.saarthi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saarthi.BuildConfig
import com.example.saarthi.data.model.Author
import com.example.saarthi.data.model.ChatMessage
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf())
    val messages = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash", // Use "gemini-pro-vision" for multi-modal
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    fun sendMessage(userInput: String) {
        // Add user's message to the list immediately
        _messages.value += ChatMessage(text = userInput, author = Author.USER)
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // --- This is the key to making the bot helpful ---
                // We provide context to the AI before the user's question.
                val systemPrompt = "You are Saarthi, a friendly and expert career and education advisor for students in India, particularly Jammu and Kashmir. Your goal is to provide supportive, clear, and encouraging advice. Answer questions related to choosing streams, career paths, college options, and skills. Keep your answers concise and easy to understand."

                val fullPrompt = "$systemPrompt\n\nUser Question: $userInput"

                val response = generativeModel.generateContent(fullPrompt)

                response.text?.let {
                    _messages.value += ChatMessage(text = it, author = Author.BOT)
                }
            } catch (e: Exception) {
                _messages.value += ChatMessage(text = "Sorry, I'm having trouble connecting. Please try again later. Error: ${e.message}", author = Author.BOT)
            } finally {
                _isLoading.value = false
            }
        }
    }
}