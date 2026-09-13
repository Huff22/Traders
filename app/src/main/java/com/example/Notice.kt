package com.example

data class Notice(
    val id: String,
    val userName: String,
    val userInitials: String,
    val timeInfo: String,
    val category: String, // "Lost Pet", "Alert", "Event"
    val content: String,
    val likes: Int,
    val isLiked: Boolean,
    val commentsCount: Int
)
