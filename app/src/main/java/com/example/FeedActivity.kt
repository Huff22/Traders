package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.databinding.ActivityFeedBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class   FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var listingAdapter: ListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomNav()
        setupListeners()
        observeViewModel()
        
        // Add inbox badge
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.nav_inbox)
        badge.backgroundColor = getColor(R.color.accent_orange)
        badge.isVisible = true
    }

    private fun setupRecyclerView() {
        listingAdapter = ListingAdapter { listing ->
            val intent = android.content.Intent(this, ProductDetailsActivity::class.java)
            intent.putExtra("LISTING_ID", listing.id)
            startActivity(intent)
        }
        binding.rvListings.apply {
            layoutManager = GridLayoutManager(this@FeedActivity, 2)
            adapter = listingAdapter
        }
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.selectedItemId = R.id.nav_explore
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> true
                R.id.nav_community -> {
                    startActivity(android.content.Intent(this, CommunityActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_barter -> {
                    startActivity(android.content.Intent(this, BarterActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_inbox -> {
                    startActivity(android.content.Intent(this, InboxActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(android.content.Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListeners() {
        val chips = listOf(binding.chipAll, binding.chipGoods, binding.chipFree, binding.chipServices)
        
        val clickListener = { view: android.view.View ->
            chips.forEach { it.isSelected = false }
            view.isSelected = true
            viewModel.setFilter((view as TextView).text.toString())
        }

        chips.forEach { it.setOnClickListener(clickListener) }
        
        // Initial state
        binding.chipAll.isSelected = true

        binding.etSearch.addTextChangedListener { text ->
            viewModel.setSearchQuery(text?.toString() ?: "")
        }

        binding.fabPost.setOnClickListener {
            startActivity(android.content.Intent(this, NewPostActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.filteredListings.collectLatest { listings ->
                listingAdapter.submitList(listings)
                binding.tvListingCount.text = "${listings.size} within 5 km"
            }
        }
    }
}
