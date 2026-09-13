package com.example

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val listingAdapter = ProfileListingAdapter { listing ->
        val intent = Intent(this, ProductDetailsActivity::class.java).apply {
            putExtra("LISTING_ID", listing.id)
        }
        startActivity(intent)
    }
    private val reviewAdapter = ProfileReviewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupBottomNav()
        setupTabs()
        loadDummyData()
    }

    private fun setupRecyclerViews() {
        binding.rvActiveListings.apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity)
            adapter = listingAdapter
        }

        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(this@ProfileActivity)
            adapter = reviewAdapter
        }
    }

    private fun setupTabs() {
        binding.tabActiveListings.setOnClickListener {
            // Update Tab UI
            binding.tvTabListingsText.setTextColor(Color.parseColor("#195E49"))
            binding.vTabListingsIndicator.setBackgroundColor(Color.parseColor("#195E49"))

            binding.tvTabReviewsText.setTextColor(Color.parseColor("#666666"))
            binding.vTabReviewsIndicator.setBackgroundColor(Color.parseColor("#E0E0E0"))

            // Update Content
            binding.llActiveListingsContainer.visibility = View.VISIBLE
            binding.rvReviews.visibility = View.GONE
        }

        binding.tabReviews.setOnClickListener {
            // Update Tab UI
            binding.tvTabReviewsText.setTextColor(Color.parseColor("#195E49"))
            binding.vTabReviewsIndicator.setBackgroundColor(Color.parseColor("#195E49"))

            binding.tvTabListingsText.setTextColor(Color.parseColor("#666666"))
            binding.vTabListingsIndicator.setBackgroundColor(Color.parseColor("#E0E0E0"))

            // Update Content
            binding.rvReviews.visibility = View.VISIBLE
            binding.llActiveListingsContainer.visibility = View.GONE
        }
    }

    private fun loadDummyData() {
        val listings = listOf(
            ProfileListing("1", "Vintage Peugeot city bike", "$240", "38 views • posted 4 hrs ago", "Live"),
            ProfileListing("2", "Espresso machine, serviced", "$95", "112 views • 3 saved", "Live"),
            ProfileListing("3", "Garden tools bundle", "$45", "Sold to Nadia R.", "Sold")
        )
        listingAdapter.submitList(listings)

        val reviews = listOf(
            ProfileReview("1", "TB", "Tomas B.", "Bought a bookshelf • 2 wks ago", 5, "Straightforward and friendly, held the item for a day while I found a van."),
            ProfileReview("2", "NR", "Nadia R.", "Bought garden tools • 1 mo ago", 5, "Everything as described and she threw in a spare set of gloves."),
            ProfileReview("3", "HC", "Hugo C.", "Barter partner • 2 mos ago", 4, "Great swap, only note is we had to reschedule once.")
        )
        reviewAdapter.submitList(reviews)
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.selectedItemId = R.id.nav_profile
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_community -> {
                    startActivity(Intent(this, CommunityActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_barter -> {
                    startActivity(Intent(this, BarterActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_inbox -> {
                    startActivity(Intent(this, InboxActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}
