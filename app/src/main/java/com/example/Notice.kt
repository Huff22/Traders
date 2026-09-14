package com.example

data class Notice(
    val id: String = "",
    val userName: String = "",
    val userInitials: String = "",
    val timeInfo: String = "",
    val category: String = "", // "Lost Pet", "Alert", "Event"
    val content: String = "",
    val likes: Int = 0,
    val isLiked: Boolean = false,
    val commentsCount: Int = 0
)
