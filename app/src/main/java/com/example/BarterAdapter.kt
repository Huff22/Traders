package com.example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemBarterBinding

class BarterAdapter(private val onItemClick: (Barter) -> Unit) :
    ListAdapter<Barter, BarterAdapter.BarterViewHolder>(BarterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarterViewHolder {
        val binding = ItemBarterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BarterViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: BarterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BarterViewHolder(
        private val binding: ItemBarterBinding,
        private val onItemClick: (Barter) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(barter: Barter) {
            binding.tvUserInitials.text = barter.userInitials
            binding.tvUserName.text = barter.userName
            binding.tvMetaInfo.text = barter.metaInfo
            binding.tvRating.text = barter.rating
            binding.tvOfferingTitle.text = barter.offeringTitle
            binding.tvOfferingDesc.text = barter.offeringDesc
            binding.tvSeekingTitle.text = barter.seekingTitle
            binding.tvSeekingDesc.text = barter.seekingDesc

            binding.root.setOnClickListener {
                onItemClick(barter)
            }
        }
    }

    class BarterDiffCallback : DiffUtil.ItemCallback<Barter>() {
        override fun areItemsTheSame(oldItem: Barter, newItem: Barter): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Barter, newItem: Barter): Boolean = oldItem == newItem
    }
}
