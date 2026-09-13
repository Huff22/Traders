package com.example

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemProfileReviewBinding

class ProfileReviewAdapter : ListAdapter<ProfileReview, ProfileReviewAdapter.ViewHolder>(ReviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemProfileReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ProfileReview) {
            binding.tvUserInitials.text = review.userInitials
            binding.tvUserName.text = review.userName
            binding.tvTransactionInfo.text = review.transactionInfo
            binding.tvReviewContent.text = review.content

            val stars = listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)
            for (i in 0 until 5) {
                if (i < review.rating) {
                    stars[i].setImageResource(R.drawable.ic_star_filled)
                } else {
                    stars[i].setImageResource(R.drawable.ic_star_outline)
                }
            }
        }
    }

    class ReviewDiffCallback : DiffUtil.ItemCallback<ProfileReview>() {
        override fun areItemsTheSame(oldItem: ProfileReview, newItem: ProfileReview): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProfileReview, newItem: ProfileReview): Boolean = oldItem == newItem
    }
}
