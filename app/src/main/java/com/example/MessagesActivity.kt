package com.example

import android.os.Bundle
import com.example.utils.FeedbackUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databinding.ActivityMessagesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesBinding
    private val viewModel: MessagesViewModel by viewModels()
    private lateinit var messagesAdapter: MessagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("USER_NAME") ?: "Dana Moreau"
        val userInitials = intent.getStringExtra("USER_INITIALS") ?: "DM"
        
        binding.tvUserName.text = userName
        binding.tvUserInitials.text = userInitials

        setupRecyclerView()
        setupListeners()
        observeViewModel()

        viewModel.loadMockMessages(userInitials)
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter()
        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(this@MessagesActivity).apply {
                stackFromEnd = true // Start from bottom
            }
            adapter = messagesAdapter
        }
    }

    private fun setupListeners() {
        binding.ivClose.setOnClickListener {
            finish()
        }

        binding.ivSend.setOnClickListener {
            val text = binding.etMessage.text.toString()
            if (text.isNotBlank()) {
                viewModel.sendMessage(text)
                binding.etMessage.text.clear()
                // Scroll to bottom
                binding.rvMessages.scrollToPosition(messagesAdapter.itemCount)
            }
        }
        
        binding.ivAttachment.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Add attachment")
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.messages.collectLatest { messages ->
                messagesAdapter.submitList(messages) {
                    if (messages.isNotEmpty()) {
                        binding.rvMessages.scrollToPosition(messages.size - 1)
                    }
                }
            }
        }
    }
}
