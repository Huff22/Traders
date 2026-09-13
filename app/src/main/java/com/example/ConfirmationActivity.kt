package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.ActivityConfirmationBinding

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val neighborhoodName = intent.getStringExtra("NEIGHBORHOOD_NAME") ?: "Colombo District"
        binding.tvSelectedNeighborhood.text = neighborhoodName

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnExplore.setOnClickListener {
            startActivity(android.content.Intent(this, FeedActivity::class.java))
            finish()
        }
    }
}
