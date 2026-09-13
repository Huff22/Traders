package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MessagesViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun loadMockMessages(userInitials: String) {
        val mockData = listOf(
            ChatMessage("1", "Hi there! Is the desk still available? I can pick it up tomorrow.", "10:02 AM", false, userInitials),
            ChatMessage("2", "Yes, it's still available! Tomorrow afternoon works for me. What time were you thinking?", "10:05 AM", true),
            ChatMessage("3", "Great! Does 2:00 PM work for you? I'll be coming from the Maplewood area.", "10:07 AM", false, userInitials)
        )
        _messages.value = mockData
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        val currentList = _messages.value.toMutableList()
        val newMessage = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = text,
            timestamp = "Just now",
            isMine = true
        )
        currentList.add(newMessage)
        _messages.value = currentList
    }
}
