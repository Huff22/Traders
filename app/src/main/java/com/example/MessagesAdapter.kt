package com.example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemChatMineBinding
import com.example.databinding.ItemChatTheirsBinding

class MessagesAdapter : ListAdapter<ChatMessage, RecyclerView.ViewHolder>(MessageDiffCallback()) {

    companion object {
        private const val TYPE_MINE = 1
        private const val TYPE_THEIRS = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isMine) TYPE_MINE else TYPE_THEIRS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_MINE) {
            val binding = ItemChatMineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MineViewHolder(binding)
        } else {
            val binding = ItemChatTheirsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TheirsViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is MineViewHolder) {
            holder.bind(message, position == 0)
        } else if (holder is TheirsViewHolder) {
            holder.bind(message)
        }
    }

    class MineViewHolder(private val binding: ItemChatMineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage, showDate: Boolean) {
            binding.tvMessage.text = message.text
            binding.tvTimestamp.text = message.timestamp
            
            if (showDate) {
                binding.tvDateBubble.visibility = android.view.View.VISIBLE
            } else {
                binding.tvDateBubble.visibility = android.view.View.GONE
            }
        }
    }

    class TheirsViewHolder(private val binding: ItemChatTheirsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) {
            binding.tvMessage.text = message.text
            binding.tvTimestamp.text = message.timestamp
            binding.tvUserInitials.text = message.userInitials ?: ""
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean = oldItem == newItem
    }
}
