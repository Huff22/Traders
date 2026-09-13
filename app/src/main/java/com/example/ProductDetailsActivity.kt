package com.example

import android.content.Intent
import android.os.Bundle
import com.example.utils.FeedbackUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.ActivityProductDetailsBinding

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listingId = intent.getStringExtra("LISTING_ID")
        val listing = DataRepository.listings.value.find { it.id == listingId }

        if (listing != null) {
            bindData(listing)
        } else {
            // Fallback bindings if no specific listing was passed
            binding.tvTitle.text = "Vintage Peugeot city bike, tuned"
            binding.tvPrice.text = "LKR 240"
        }

        setupListeners()
    }

    private fun bindData(listing: Listing) {
        binding.tvTitle.text = listing.title
        binding.tvPrice.text = if (listing.isFree) "Free" else listing.price
        binding.tvCategoryTag.text = "${listing.category} • Default"
        binding.tvDistanceTag.text = listing.distance
        
        binding.tvSellerInitials.text = listing.userInitials
        // Extrapolate a bit from initials for a dummy name
        binding.tvSellerName.text = "${listing.userInitials} User"
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnCall.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Calling seller...")
        }

        binding.btnMessage.setOnClickListener {
            // Open Messages Activity
            startActivity(Intent(this, MessagesActivity::class.java))
        }
    }
}
