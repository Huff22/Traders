package com.example

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.example.databinding.ActivityNewPostBinding
import com.example.databinding.ItemPostOptionBinding
import com.example.utils.FeedbackUtils

class NewPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewPostBinding
    
    private var selectedType = "SELL" // SELL, FREE, SERVICE, NOTICE, BARTER
    private var selectedImageUri: Uri? = null
    
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            binding.ivSelectedImage.setImageURI(uri)
            binding.ivSelectedImage.visibility = View.VISIBLE
            binding.llImagePlaceholder.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupOptions()
        setupListeners()
        updateFormVisibility()
    }

    private fun setupOptions() {
        val optSell = ItemPostOptionBinding.bind(binding.optSell.root)
        optSell.ivIcon.setImageResource(R.drawable.ic_grid)
        optSell.tvTitle.text = "Sell an item"
        optSell.tvDesc.text = "Goods with a price, photos, pickup spot"
        
        val optFree = ItemPostOptionBinding.bind(binding.optFree.root)
        optFree.ivIcon.setImageResource(R.drawable.ic_diamond)
        optFree.tvTitle.text = "Give away free"
        optFree.tvDesc.text = "Anything going to a neighbor at no cost"

        val optService = ItemPostOptionBinding.bind(binding.optService.root)
        optService.ivIcon.setImageResource(R.drawable.ic_pencil)
        optService.tvTitle.text = "Offer a service"
        optService.tvDesc.text = "Hourly or per-job local work"

        val optNotice = ItemPostOptionBinding.bind(binding.optNotice.root)
        optNotice.ivIcon.setImageResource(R.drawable.ic_notice)
        optNotice.tvTitle.text = "Post a notice"
        optNotice.tvDesc.text = "Alerts, lost pets, events, requests"

        val optBarter = ItemPostOptionBinding.bind(binding.optBarter.root)
        optBarter.ivIcon.setImageResource(R.drawable.ic_handshake)
        optBarter.tvTitle.text = "Barter skills & services"
        optBarter.tvDesc.text = "Trade time and skills with neighbors"

        val options = listOf(
            Pair(optSell, "SELL"),
            Pair(optFree, "FREE"),
            Pair(optService, "SERVICE"),
            Pair(optNotice, "NOTICE"),
            Pair(optBarter, "BARTER")
        )

        options.forEach { (optBinding, type) ->
            optBinding.root.setOnClickListener {
                selectedType = type
                // Update selection UI
                options.forEach { (b, t) ->
                    val isSelected = t == selectedType
                    b.ivCheck.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
                    (b.root as MaterialCardView).strokeColor = if (isSelected) Color.parseColor("#195E49") else Color.parseColor("#E0E0E0")
                }
                updateFormVisibility()
            }
        }
        
        // Initial Selection
        optSell.root.performClick()
    }

    private fun updateFormVisibility() {
        // Reset all
        binding.lblTitle.visibility = View.VISIBLE
        binding.etTitle.visibility = View.VISIBLE
        binding.llPriceCategory.visibility = View.VISIBLE
        binding.llPrice.visibility = View.VISIBLE
        binding.llCategory.visibility = View.VISIBLE
        binding.lblDescription.visibility = View.VISIBLE
        binding.etDescription.visibility = View.VISIBLE
        binding.lblSeekingTitle.visibility = View.GONE
        binding.etSeekingTitle.visibility = View.GONE
        binding.lblSeekingDesc.visibility = View.GONE
        binding.etSeekingDesc.visibility = View.GONE

        when (selectedType) {
            "SELL" -> {
                binding.lblTitle.text = "Title"
                binding.etCategory.setText("Goods")
                binding.etCategory.isEnabled = false
                binding.lblDescription.visibility = View.GONE
                binding.etDescription.visibility = View.GONE
            }
            "FREE" -> {
                binding.lblTitle.text = "Title"
                binding.llPrice.visibility = View.GONE
                binding.etCategory.setText("Goods")
                binding.etCategory.isEnabled = false
                binding.lblDescription.visibility = View.GONE
                binding.etDescription.visibility = View.GONE
            }
            "SERVICE" -> {
                binding.lblTitle.text = "Service Title"
                binding.etCategory.setText("Services")
                binding.etCategory.isEnabled = false
            }
            "NOTICE" -> {
                binding.lblTitle.visibility = View.GONE
                binding.etTitle.visibility = View.GONE
                binding.llPrice.visibility = View.GONE
                binding.etCategory.isEnabled = true
                binding.etCategory.setText("")
                binding.etCategory.hint = "Event, Alert, Lost Pet"
            }
            "BARTER" -> {
                binding.lblTitle.text = "Offering Title"
                binding.llPriceCategory.visibility = View.GONE
                binding.lblDescription.text = "Offering Description"
                
                binding.lblSeekingTitle.visibility = View.VISIBLE
                binding.etSeekingTitle.visibility = View.VISIBLE
                binding.lblSeekingDesc.visibility = View.VISIBLE
                binding.etSeekingDesc.visibility = View.VISIBLE
            }
        }
    }

    private fun setupListeners() {
        binding.ivClose.setOnClickListener { finish() }

        binding.llImagePicker.setOnClickListener {
            pickMedia.launch(androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnPublish.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val price = binding.etPrice.text.toString()
            val category = binding.etCategory.text.toString()
            val desc = binding.etDescription.text.toString()
            val seekingTitle = binding.etSeekingTitle.text.toString()
            val seekingDesc = binding.etSeekingDesc.text.toString()

            val id = System.currentTimeMillis().toString()

            when (selectedType) {
                "SELL", "FREE", "SERVICE" -> {
                    if (title.isBlank()) {
                        FeedbackUtils.showError(binding.root, "Title is required")
                        return@setOnClickListener
                    }
                    val isFree = selectedType == "FREE"
                    val itemPrice = if (isFree) "Free" else price
                    val listing = Listing(id, title, itemPrice, category, isFree, "0.1 km", "ME", "New user")
                    DataRepository.addListing(listing)
                }
                "NOTICE" -> {
                    if (category.isBlank() || desc.isBlank()) {
                        FeedbackUtils.showError(binding.root, "Category and description are required")
                        return@setOnClickListener
                    }
                    val notice = Notice(id, "Me", "ME", "Just now", category, desc, 0, false, 0)
                    DataRepository.addNotice(notice)
                }
                "BARTER" -> {
                    if (title.isBlank() || desc.isBlank() || seekingTitle.isBlank() || seekingDesc.isBlank()) {
                        FeedbackUtils.showError(binding.root, "All fields are required")
                        return@setOnClickListener
                    }
                    val barter = Barter(id, "Me", "ME", "Just now", "5.0", title, desc, seekingTitle, seekingDesc)
                    DataRepository.addBarter(barter)
                }
            }
            
            FeedbackUtils.showSuccess(binding.root, "Published!")
            finish()
        }
    }
}
