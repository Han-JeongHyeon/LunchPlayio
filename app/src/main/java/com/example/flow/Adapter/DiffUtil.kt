package com.example.flow.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.flow.Data.CheckList

class DiffUtil : DiffUtil.ItemCallback<CheckList>() {
    override fun areItemsTheSame(
        oldItem: CheckList,
        newItem: CheckList
    ): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(
        oldItem: CheckList,
        newItem: CheckList
    ): Boolean {
        return oldItem == newItem
    }
}