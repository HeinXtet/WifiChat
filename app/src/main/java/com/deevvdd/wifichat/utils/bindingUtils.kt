package com.deevvdd.wifichat.utils

import android.text.format.DateUtils
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deevvdd.wifichat.domain.model.Message
import com.deevvdd.wifichat.ui.common.ChatAdapter
import java.util.Date

/**
 * Created by heinhtet deevvdd@gmail.com on 06,August,2021
 */

@BindingAdapter("adapter")
fun bindRecyclerView(recyclerView: RecyclerView, adapter: ListAdapter<*, *>?) {
    recyclerView.adapter = adapter
}

@BindingAdapter("items")
fun bindItems(recyclerView: RecyclerView, items: List<Message>?) {
    items?.let {
        if (recyclerView.adapter is ChatAdapter) {
            (recyclerView.adapter as ChatAdapter).submitList(items)
            recyclerView.apply {
                this.postDelayed(
                    {
                        this.smoothScrollToPosition((items.count() - 1))
                    }, 600
                )
            }
        }
    }
}

@BindingAdapter("readableDate")
fun bindServerDate(textView: TextView, date: Date?) {
    date?.let {
        textView.text = DateUtils.getRelativeTimeSpanString(date.time)
    }
}

@BindingAdapter("intToString")
fun bindInt(textView: TextView, anyInt: Int) {
    textView.text = anyInt.toString()
}
