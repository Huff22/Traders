package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databinding.ActivityInboxBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InboxActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInboxBinding
    private val viewModel: InboxViewModel by viewModels()
    private lateinit var inboxAdapter: InboxAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomNav()
        setupListeners()
        observeViewModel()
        
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.nav_inbox)
        badge.backgroundColor = getColor(R.color.accent_orange)
        badge.isVisible = true
    }

    private fun setupRecyclerView() {
        inboxAdapter = InboxAdapter { thread ->
            val intent = Intent(this, MessagesActivity::class.java).apply {
                putExtra("USER_NAME", thread.userName)
                putExtra("USER_INITIALS", thread.userInitials)
            }
            startActivity(intent)
        }
        binding.rvInbox.apply {
            layoutManager = LinearLayoutManager(this@InboxActivity)
            adapter = inboxAdapter
        }
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.selectedItemId = R.id.nav_inbox
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
                R.id.nav_inbox -> true
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
        binding.etSearch.addTextChangedListener { text ->
            viewModel.setSearchQuery(text?.toString() ?: "")
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.filteredThreads.collectLatest { threads ->
                inboxAdapter.submitList(threads)
            }
        }
    }
}
