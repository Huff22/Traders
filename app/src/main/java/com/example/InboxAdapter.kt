package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemInboxBinding

class InboxAdapter(private val onItemClick: (InboxThread) -> Unit) :
    ListAdapter<InboxThread, InboxAdapter.InboxViewHolder>(InboxDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        val binding = ItemInboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InboxViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class InboxViewHolder(
        private val binding: ItemInboxBinding,
        private val onItemClick: (InboxThread) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(thread: InboxThread) {
            binding.tvUserInitials.text = thread.userInitials
            binding.tvUserName.text = thread.userName
            binding.tvLastMessage.text = thread.lastMessage
            binding.tvTimestamp.text = thread.timestamp
            binding.tvTopic.text = thread.topic
            
            binding.vUnreadDot.visibility = if (thread.hasUnread) View.VISIBLE else View.INVISIBLE

            binding.root.setOnClickListener {
                onItemClick(thread)
            }
        }
    }

    class InboxDiffCallback : DiffUtil.ItemCallback<InboxThread>() {
        override fun areItemsTheSame(oldItem: InboxThread, newItem: InboxThread): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: InboxThread, newItem: InboxThread): Boolean = oldItem == newItem
    }
}
