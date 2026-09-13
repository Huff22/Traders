package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine

class CommunityViewModel : ViewModel() {

    private val allNotices = listOf(
        Notice("1", "Elena V.", "EV", "5 yrs local • 20 min ago • 0.3 km", "Lost Pet", "Our tabby Miso slipped out near the Willow St. playground around 7pm. He is chipped, answers to his name, very shy with strangers. Please check garages and sheds.", 34, true, 12),
        Notice("2", "Carlos B.", "CB", "12 yrs local • 2 hrs ago", "Event", "Saturday street cleanup starts 9am at the corner shop. Gloves and bags provided, coffee after. Kids welcome, we finish by noon.", 61, false, 8),
        Notice("3", "Grace T.", "GT", "8 yrs local • yesterday", "Event", "Starting a Tuesday evening running group from the library steps, easy 5k pace. Anyone is welcome, no signup needed.", 47, true, 19),
        Notice("4", "Nadia R.", "NR", "3 yrs local • 4 hrs ago", "Alert", "Water main work on Hale Avenue tomorrow, expect the road closed between 8am and 4pm. Detour signs are already up.", 22, false, 5)
    )

    private val _currentFilter = MutableStateFlow("All") // All, Alerts, Lost Pet, Events
    val currentFilter: StateFlow<String> = _currentFilter

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _notices = DataRepository.notices

    val filteredNotices = combine(_notices, _currentFilter, _searchQuery) { notices, filter, query ->
        val categoryFiltered = when (filter) {
            "All" -> notices
            "Alerts" -> notices.filter { it.category == "Alert" }
            "Lost Pet" -> notices.filter { it.category == "Lost Pet" }
            "Events" -> notices.filter { it.category == "Event" }
            else -> notices
        }
        
        if (query.isBlank()) {
            categoryFiltered
        } else {
            categoryFiltered.filter { 
                it.content.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true) || it.userName.contains(query, ignoreCase = true)
            }
        }
    }

    fun setFilter(filter: String) {
        _currentFilter.value = filter
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
