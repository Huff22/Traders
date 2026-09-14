package com.example

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

object DataRepository {
    private val db: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    private val mockListings = listOf(
        Listing("1", "Weekend dog walking", "LKR 15 / walk", "Services", false, "1.2 km", "RK", "3 yrs local"),
        Listing("2", "Piano lessons for kids", "LKR 30 / hr", "Services", false, "1.2 km", "RK", "3 yrs local"),
        Listing("3", "Vintage Peugeot city bike", "LKR 240", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("4", "Oak dining table, seats 6", "LKR 180", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("5", "Moving boxes, 20 of them", "Free", "Goods", true, "1.2 km", "RK", "3 yrs local"),
        Listing("6", "Espresso machine, serviced", "LKR 95", "Goods", false, "1.2 km", "RK", "3 yrs local"),
        Listing("7", "Sourdough starter + jar", "Free", "Goods", true, "1.2 km", "RK", "3 yrs local"),
        Listing("8", "Bookshelf, solid pine", "LKR 60", "Goods", false, "1.2 km", "RK", "3 yrs local")
    )

    private val mockNotices = listOf(
        Notice("1", "Elena V.", "EV", "5 yrs local • 20 min ago • 0.3 km", "Lost Pet", "Our tabby Miso slipped out near the Willow St. playground around 7pm. He is chipped, answers to his name, very shy with strangers. Please check garages and sheds.", 34, true, 12),
        Notice("2", "Carlos B.", "CB", "12 yrs local • 2 hrs ago", "Event", "Saturday street cleanup starts 9am at the corner shop. Gloves and bags provided, coffee after. Kids welcome, we finish by noon.", 61, false, 8),
        Notice("3", "Grace T.", "GT", "8 yrs local • yesterday", "Event", "Starting a Tuesday evening running group from the library steps, easy 5k pace. Anyone is welcome, no signup needed.", 47, true, 19),
        Notice("4", "Nadia R.", "NR", "3 yrs local • 4 hrs ago", "Alert", "Water main work on Hale Avenue tomorrow, expect the road closed between 8am and 4pm. Detour signs are already up.", 22, false, 5)
    )

    private val mockBarters = listOf(
        Barter("1", "Samir K.", "SK", "7 yrs local • 0.9 km", "4.8", "Bike tune-ups", "Brakes, gears, wheel truing", "Sourdough baking lesson", "Weekend afternoons"),
        Barter("2", "Lena P.", "LP", "4 yrs local • 1.6 km", "5.0", "Spanish conversation hours", "2 hrs per week, native speaker", "Help repainting a hallway", "One weekend, paint provided"),
        Barter("3", "Omar M.", "OM", "10 yrs local • 2.2 km", "4.7", "Tax filing help", "Simple returns, certified", "Dog sitting in August", "Two weeks, small terrier")
    )

    val listings = MutableStateFlow<List<Listing>>(mockListings)
    val notices = MutableStateFlow<List<Notice>>(mockNotices)
    val barters = MutableStateFlow<List<Barter>>(mockBarters)

    init {
        // Listen to Firestore updates and combine them with mock data
        db.collection("listings").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("DataRepository", "Listen failed for listings.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val firestoreListings = snapshot.documents.mapNotNull { it.toObject(Listing::class.java) }
                listings.value = firestoreListings + mockListings
            }
        }

        db.collection("notices").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("DataRepository", "Listen failed for notices.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val firestoreNotices = snapshot.documents.mapNotNull { it.toObject(Notice::class.java) }
                notices.value = firestoreNotices + mockNotices
            }
        }

        db.collection("barters").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("DataRepository", "Listen failed for barters.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val firestoreBarters = snapshot.documents.mapNotNull { it.toObject(Barter::class.java) }
                barters.value = firestoreBarters + mockBarters
            }
        }
    }

    fun addListing(listing: Listing) {
        // Generate a new ID if it doesn't have one (or use the one provided)
        val id = if (listing.id.isEmpty() || listing.id.length < 5) db.collection("listings").document().id else listing.id
        val newListing = listing.copy(id = id)
        
        db.collection("listings").document(id)
            .set(newListing)
            .addOnFailureListener { e ->
                Log.w("DataRepository", "Error adding listing", e)
            }
    }

    fun addNotice(notice: Notice) {
        val id = if (notice.id.isEmpty() || notice.id.length < 5) db.collection("notices").document().id else notice.id
        val newNotice = notice.copy(id = id)

        db.collection("notices").document(id)
            .set(newNotice)
            .addOnFailureListener { e ->
                Log.w("DataRepository", "Error adding notice", e)
            }
    }

    fun addBarter(barter: Barter) {
        val id = if (barter.id.isEmpty() || barter.id.length < 5) db.collection("barters").document().id else barter.id
        val newBarter = barter.copy(id = id)

        db.collection("barters").document(id)
            .set(newBarter)
            .addOnFailureListener { e ->
                Log.w("DataRepository", "Error adding barter", e)
            }
    }
}
