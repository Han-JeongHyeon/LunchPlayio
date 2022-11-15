package com.example.flow.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.flow.Data.CheckList

class DiffUtil : DiffUtil.ItemCallback<CheckList>() {
    override fun areItemsTheSame(
        oldItem: CheckList,
        newItem: CheckList
    ): Boolean {
        return oldItem.data.id == newItem.data.id
    }

    override fun areContentsTheSame(
        oldItem: CheckList,
        newItem: CheckList
    ): Boolean {
        return oldItem == newItem
    }
}