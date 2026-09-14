package com.example

data class Listing(
    val id: String = "",
    val title: String = "",
    val price: String = "",
    val category: String = "", // "Goods", "Services"
    val isFree: Boolean = false,
    val distance: String = "",
    val userInitials: String = "",
    val trustLabel: String = ""
)
