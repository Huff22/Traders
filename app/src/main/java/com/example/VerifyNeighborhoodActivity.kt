package com.example

import android.content.Intent
import android.os.Bundle
import com.example.utils.FeedbackUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.ActivityVerifyNeighborhoodBinding

class VerifyNeighborhoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyNeighborhoodBinding
    private val viewModel: VerifyNeighborhoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyNeighborhoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnGps.setOnClickListener {
            FeedbackUtils.showInfo(binding.root, "Requesting GPS Location...")
            // In a real app, handle permission and fetch location here
            // For now, simulate selecting a location
            viewModel.selectNeighborhood("Malabe")
            navigateToConfirmation("Malabe")
        }

        binding.ivClearSearch.setOnClickListener {
            binding.etSearch.text.clear()
        }

        binding.llResultMalabe.setOnClickListener {
            viewModel.selectNeighborhood("Malabe")
            // Visual feedback could go here
        }

        binding.btnConfirm.setOnClickListener {
            val selected = viewModel.selectedNeighborhood.value
            if (selected != null) {
                navigateToConfirmation(selected)
            } else {
                FeedbackUtils.showError(binding.root, "Please select a neighborhood first")
            }
        }
    }
    
    private fun navigateToConfirmation(neighborhood: String) {
        val intent = Intent(this, ConfirmationActivity::class.java)
        intent.putExtra("NEIGHBORHOOD_NAME", neighborhood)
        startActivity(intent)
        finish()
    }
}
