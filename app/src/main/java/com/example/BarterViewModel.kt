package com.example

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BarterViewModel : ViewModel() {

    private val allBarters = listOf(
        Barter(
            "1", "Samir K.", "SK", "7 yrs local • 0.9 km", "4.8",
            "Bike tune-ups", "Brakes, gears, wheel truing",
            "Sourdough baking lesson", "Weekend afternoons"
        ),
        Barter(
            "2", "Lena P.", "LP", "4 yrs local • 1.6 km", "5.0",
            "Spanish conversation hours", "2 hrs per week, native speaker",
            "Help repainting a hallway", "One weekend, paint provided"
        ),
        Barter(
            "3", "Omar M.", "OM", "10 yrs local • 2.2 km", "4.7",
            "Tax filing help", "Simple returns, certified",
            "Dog sitting in August", "Two weeks, small terrier"
        )
    )

    private val _barters = DataRepository.barters
    val barters: StateFlow<List<Barter>> = _barters
}
