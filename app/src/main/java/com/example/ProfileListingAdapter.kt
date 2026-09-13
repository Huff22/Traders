package com.example

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemProfileListingBinding

class ProfileListingAdapter(private val onClick: (ProfileListing) -> Unit) : ListAdapter<ProfileListing, ProfileListingAdapter.ViewHolder>(ListingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemProfileListingBinding, private val onClick: (ProfileListing) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listing: ProfileListing) {
            binding.tvTitle.text = listing.title
            binding.tvPrice.text = listing.price
            binding.tvMetaInfo.text = listing.metaInfo
            binding.tvStatusBadge.text = listing.status

            if (listing.status == "Live") {
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_live)
                binding.tvStatusBadge.setTextColor(Color.parseColor("#195E49"))
            } else {
                binding.tvStatusBadge.setBackgroundResource(R.drawable.bg_badge_sold)
                binding.tvStatusBadge.setTextColor(Color.parseColor("#666666"))
            }
            
            binding.root.setOnClickListener {
                onClick(listing)
            }
        }
    }

    class ListingDiffCallback : DiffUtil.ItemCallback<ProfileListing>() {
        override fun areItemsTheSame(oldItem: ProfileListing, newItem: ProfileListing): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProfileListing, newItem: ProfileListing): Boolean = oldItem == newItem
    }
}
