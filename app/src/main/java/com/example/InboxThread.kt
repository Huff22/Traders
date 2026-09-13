package com.example

data class InboxThread(
    val id: String,
    val userInitials: String,
    val userName: String,
    val lastMessage: String,
    val timestamp: String,
    val topic: String,
    val hasUnread: Boolean
)
