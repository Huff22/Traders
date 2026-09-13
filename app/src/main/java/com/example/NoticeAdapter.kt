package com.example

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemNoticeBinding

class NoticeAdapter : ListAdapter<Notice, NoticeAdapter.NoticeViewHolder>(NoticeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoticeViewHolder(private val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: Notice) {
            binding.tvUserInitials.text = notice.userInitials
            binding.tvUserName.text = notice.userName
            binding.tvTimeInfo.text = notice.timeInfo
            binding.tvContent.text = notice.content
            binding.tvCategoryBadge.text = notice.category
            binding.tvLikesCount.text = notice.likes.toString()
            binding.tvCommentsCount.text = "${notice.commentsCount} comments"

            // Style category badge
            when (notice.category) {
                "Lost Pet" -> {
                    binding.tvCategoryBadge.setBackgroundResource(R.drawable.bg_badge_lost_pet)
                    binding.tvCategoryBadge.setTextColor(Color.parseColor("#D84315"))
                }
                "Alert" -> {
                    binding.tvCategoryBadge.setBackgroundResource(R.drawable.bg_badge_alert)
                    binding.tvCategoryBadge.setTextColor(Color.parseColor("#D84315"))
                }
                "Event" -> {
                    binding.tvCategoryBadge.setBackgroundResource(R.drawable.bg_badge_event)
                    binding.tvCategoryBadge.setTextColor(Color.parseColor("#2E7D32"))
                }
            }

            // Like status
            if (notice.isLiked) {
                binding.ivLike.setImageResource(R.drawable.ic_heart_filled)
            } else {
                binding.ivLike.setImageResource(R.drawable.ic_heart_outline)
            }
        }
    }

    class NoticeDiffCallback : DiffUtil.ItemCallback<Notice>() {
        override fun areItemsTheSame(oldItem: Notice, newItem: Notice): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Notice, newItem: Notice): Boolean = oldItem == newItem
    }
}
