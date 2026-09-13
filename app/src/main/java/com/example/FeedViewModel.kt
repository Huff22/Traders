package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.combine

class FeedViewModel : ViewModel() {

    // Dummy Data based on screenshots
    private val allListings = listOf(
        Listing("1", "Weekend dog walking", "LKR 15 / walk", "Services", false, "1.2 km", "RK", "3 yrs local"),
        Listing("2", "Piano lessons for kids", "LKR 30 / hr", "Services", false, "1.2 km", "RK", "3 yrs local"),
        Listing("3", "Vintage Peugeot city bike", "LKR 240", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("4", "Oak dining table, seats 6", "LKR 180", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("5", "Moving boxes, 20 of them", "Free", "Goods", true, "1.2 km", "RK", "3 yrs local"),
        Listing("6", "Espresso machine, serviced", "LKR 95", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("7", "Sourdough starter + jar", "Free", "Goods", true, "1.2 km", "RK", "3 yrs local"),
        Listing("8", "Bookshelf, solid pine", "LKR 60", "Goods", false, "1.2 km", "RK", "3 yrs local")
    )

    private val _currentFilter = MutableStateFlow("All") // All, Goods, Free, Services
    val currentFilter: StateFlow<String> = _currentFilter

    private val _listings = DataRepository.listings

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredListings = combine(_listings, _currentFilter, _searchQuery) { listings, filter, query ->
        val categoryFiltered = when (filter) {
            "All" -> listings
            "Goods" -> listings.filter { it.category == "Goods" }
            "Free" -> listings.filter { it.isFree }
            "Services" -> listings.filter { it.category == "Services" }
            else -> listings
        }
        
        if (query.isBlank()) {
            categoryFiltered
        } else {
            categoryFiltered.filter { 
                it.title.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
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
