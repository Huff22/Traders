package com.example

data class ChatMessage(
    val id: String,
    val text: String,
    val timestamp: String,
    val isMine: Boolean,
    val userInitials: String? = null // Only needed if it's theirs
)
