package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VerifyNeighborhoodViewModel : ViewModel() {
    
    private val _selectedNeighborhood = MutableStateFlow<String?>(null)
    val selectedNeighborhood: StateFlow<String?> = _selectedNeighborhood

    fun selectNeighborhood(name: String) {
        _selectedNeighborhood.value = name
    }
    
    fun clearSelection() {
        _selectedNeighborhood.value = null
    }
}
