package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databinding.ActivityCommunityBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CommunityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityBinding
    private val viewModel: CommunityViewModel by viewModels()
    private lateinit var noticeAdapter: NoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
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
        noticeAdapter = NoticeAdapter()
        binding.rvNotices.apply {
            layoutManager = LinearLayoutManager(this@CommunityActivity)
            adapter = noticeAdapter
        }
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.selectedItemId = R.id.nav_community
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    startActivity(Intent(this, FeedActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_community -> true
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
        val chips = listOf(binding.chipAll, binding.chipAlerts, binding.chipLostPet, binding.chipEvents)
        
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

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.filteredNotices.collectLatest { notices ->
                noticeAdapter.submitList(notices)
            }
        }
    }
}
