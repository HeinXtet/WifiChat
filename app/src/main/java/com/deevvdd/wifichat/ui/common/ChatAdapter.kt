package com.deevvdd.wifichat.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deevvdd.wifichat.databinding.ItemClientMessageBinding
import com.deevvdd.wifichat.databinding.ItemMessageBinding
import com.deevvdd.wifichat.domain.model.Message
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */
class ChatAdapter(private val name: String) :
    ListAdapter<Message, RecyclerView.ViewHolder>(diffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Timber.d("view type $viewType name $name")
        return when (viewType) {
            USER_TYPE -> MessageViewHolder(
                ItemMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            CLIENT_TYPE ->
                MessageClientViewHolder(
                    ItemClientMessageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            else -> MessageViewHolder(
                ItemMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MessageViewHolder) {
            holder.onBind(getItem(position))
        } else if (holder is MessageClientViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.senderName == name) {
            USER_TYPE
        } else {
            CLIENT_TYPE
        }
    }

    override fun submitList(list: List<Message>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    companion object {

        const val USER_TYPE = 1
        const val CLIENT_TYPE = 2

        val diffUtils = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.date.time == newItem.date.time
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }
    }
}
