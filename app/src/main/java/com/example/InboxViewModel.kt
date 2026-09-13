package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

class InboxViewModel : ViewModel() {

    private val allThreads = listOf(
        InboxThread("1", "DM", "Dana M.", "Sure, the bike is still available — around 6?", "9:12", "Vintage Peugeot city bike", true),
        InboxThread("2", "SK", "Samir K.", "Barter proposal: tune-up for baking lesson", "8:40", "Barter • pending", true),
        InboxThread("3", "AL", "Amira L.", "Boxes are on the porch, help yourself.", "Yesterday", "Moving boxes, 20 of them", true),
        InboxThread("4", "TB", "Tomas B.", "Thanks again for Saturday!", "Mon", "Weekend dog walking", false),
        InboxThread("5", "CB", "Carlos B.", "Cleanup list is attached below.", "Sun", "Notice board reply", false)
    )

    private val _threads = MutableStateFlow(allThreads)
    private val _searchQuery = MutableStateFlow("")

    val filteredThreads = combine(_threads, _searchQuery) { threads, query ->
        if (query.isBlank()) {
            threads
        } else {
            threads.filter {
                it.userName.contains(query, ignoreCase = true) ||
                it.lastMessage.contains(query, ignoreCase = true) ||
                it.topic.contains(query, ignoreCase = true)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
