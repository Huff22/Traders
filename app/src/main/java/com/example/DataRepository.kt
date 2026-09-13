package com.example

import kotlinx.coroutines.flow.MutableStateFlow

object DataRepository {
    val listings = MutableStateFlow(listOf(
        Listing("1", "Weekend dog walking", "LKR 15 / walk", "Services", false, "1.2 km", "RK", "3 yrs local"),
        Listing("2", "Piano lessons for kids", "LKR 30 / hr", "Services", false, "1.2 km", "RK", "3 yrs local"),
        Listing("3", "Vintage Peugeot city bike", "LKR 240", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("4", "Oak dining table, seats 6", "LKR 180", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("5", "Moving boxes, 20 of them", "Free", "Goods", true, "1.2 km", "RK", "3 yrs local"),
        Listing("6", "Espresso machine, serviced", "LKR 95", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("7", "Sourdough starter + jar", "Free", "Goods", true, "1.2 km", "RK", "3 yrs local"),
        Listing("8", "Bookshelf, solid pine", "LKR 60", "Goods", false, "1.2 km", "RK", "3 yrs local")
    ))

    val notices = MutableStateFlow(listOf(
        Notice("1", "Elena V.", "EV", "5 yrs local • 20 min ago • 0.3 km", "Lost Pet", "Our tabby Miso slipped out near the Willow St. playground around 7pm. He is chipped, answers to his name, very shy with strangers. Please check garages and sheds.", 34, true, 12),
        Notice("2", "Carlos B.", "CB", "12 yrs local • 2 hrs ago", "Event", "Saturday street cleanup starts 9am at the corner shop. Gloves and bags provided, coffee after. Kids welcome, we finish by noon.", 61, false, 8),
        Notice("3", "Grace T.", "GT", "8 yrs local • yesterday", "Event", "Starting a Tuesday evening running group from the library steps, easy 5k pace. Anyone is welcome, no signup needed.", 47, true, 19),
        Notice("4", "Nadia R.", "NR", "3 yrs local • 4 hrs ago", "Alert", "Water main work on Hale Avenue tomorrow, expect the road closed between 8am and 4pm. Detour signs are already up.", 22, false, 5)
    ))

    val barters = MutableStateFlow(listOf(
        Barter("1", "Samir K.", "SK", "7 yrs local • 0.9 km", "4.8", "Bike tune-ups", "Brakes, gears, wheel truing", "Sourdough baking lesson", "Weekend afternoons"),
        Barter("2", "Lena P.", "LP", "4 yrs local • 1.6 km", "5.0", "Spanish conversation hours", "2 hrs per week, native speaker", "Help repainting a hallway", "One weekend, paint provided"),
        Barter("3", "Omar M.", "OM", "10 yrs local • 2.2 km", "4.7", "Tax filing help", "Simple returns, certified", "Dog sitting in August", "Two weeks, small terrier")
    ))

    fun addListing(listing: Listing) {
        listings.value = listOf(listing) + listings.value
    }

    fun addNotice(notice: Notice) {
        notices.value = listOf(notice) + notices.value
    }

    fun addBarter(barter: Barter) {
        barters.value = listOf(barter) + barters.value
    }
}
