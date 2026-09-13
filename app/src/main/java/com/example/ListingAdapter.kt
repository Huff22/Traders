package com.example

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemListingBinding

class ListingAdapter(private val onItemClick: (Listing) -> Unit) :
    ListAdapter<Listing, ListingAdapter.ListingViewHolder>(ListingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val binding = ItemListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListingViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListingViewHolder(
        private val binding: ItemListingBinding,
        private val onItemClick: (Listing) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listing: Listing) {
            binding.tvTitle.text = listing.title
            binding.tvPrice.text = if (listing.isFree) "Free" else listing.price
            binding.tvDistance.text = listing.distance
            binding.tvUserInitials.text = listing.userInitials
            binding.tvTrustLabel.text = listing.trustLabel
            
            // Assign some placeholder colors based on category
            val color = when(listing.category) {
                "Goods" -> Color.parseColor("#BDBDBD")
                "Services" -> Color.parseColor("#90A4AE")
                else -> Color.parseColor("#E0E0E0")
            }
            binding.ivListingImage.setBackgroundColor(color)

            binding.root.setOnClickListener {
                onItemClick(listing)
            }
        }
    }

    class ListingDiffCallback : DiffUtil.ItemCallback<Listing>() {
        override fun areItemsTheSame(oldItem: Listing, newItem: Listing): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Listing, newItem: Listing): Boolean {
            return oldItem == newItem
        }
    }
}
