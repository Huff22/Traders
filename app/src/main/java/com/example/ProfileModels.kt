package com.example

data class ProfileListing(
    val id: String,
    val title: String,
    val price: String,
    val metaInfo: String,
    val status: String // "Live" or "Sold"
)

data class ProfileReview(
    val id: String,
    val userInitials: String,
    val userName: String,
    val transactionInfo: String,
    val rating: Int, // 1 to 5
    val content: String
)
