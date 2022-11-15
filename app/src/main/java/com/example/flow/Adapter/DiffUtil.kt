package com.example.flow.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.flow.Data.GetTableData

class DiffUtil : DiffUtil.ItemCallback<GetTableData>() {
    override fun areItemsTheSame(
        oldItem: GetTableData,
        newItem: GetTableData
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GetTableData,
        newItem: GetTableData
    ): Boolean {
        return oldItem == newItem
    }
}