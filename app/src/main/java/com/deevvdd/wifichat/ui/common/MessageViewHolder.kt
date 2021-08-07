package com.deevvdd.wifichat.ui.common

import androidx.recyclerview.widget.RecyclerView
import com.deevvdd.wifichat.databinding.ItemMessageBinding
import com.deevvdd.wifichat.domain.model.Message

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
class MessageViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: Message) {
        binding.apply {
            message = item
        }
    }
}
