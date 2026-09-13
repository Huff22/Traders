package com.example

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import com.example.utils.FeedbackUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databinding.ActivityBarterBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BarterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBarterBinding
    private val viewModel: BarterViewModel by viewModels()
    private lateinit var barterAdapter: BarterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarterBinding.inflate(layoutInflater)
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
        barterAdapter = BarterAdapter { barter ->
            showBarterDialog(barter)
        }
        binding.rvBarters.apply {
            layoutManager = LinearLayoutManager(this@BarterActivity)
            adapter = barterAdapter
        }
    }

    private fun showBarterDialog(barter: Barter) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_barter_detail)
        
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            android.view.ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<TextView>(R.id.tvDialogTitle).text = "${barter.userName}'s Barter"
        dialog.findViewById<TextView>(R.id.tvOfferingDialogText).text = "${barter.offeringTitle}\n(${barter.offeringDesc})"
        dialog.findViewById<TextView>(R.id.tvSeekingDialogText).text = "${barter.seekingTitle}\n(${barter.seekingDesc})"
        
        dialog.findViewById<MaterialButton>(R.id.btnConnect).setOnClickListener {
            val intent = Intent(this, MessagesActivity::class.java).apply {
                putExtra("USER_NAME", barter.userName)
                putExtra("USER_INITIALS", barter.userInitials)
            }
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupBottomNav() {
        binding.bottomNavigationView.selectedItemId = R.id.nav_barter
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
                R.id.nav_barter -> true
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
        binding.fabPost.setOnClickListener {
            startActivity(android.content.Intent(this, NewPostActivity::class.java))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.barters.collectLatest { barters ->
                barterAdapter.submitList(barters)
            }
        }
    }
}
