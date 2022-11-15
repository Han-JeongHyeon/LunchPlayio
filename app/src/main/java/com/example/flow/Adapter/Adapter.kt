package com.example.flow.Adapter

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flow.Data.CheckList
import com.example.flow.Data.GetTableData
import com.example.flow.R
import com.example.flow.databinding.CheckListBinding

class Adapter :
    ListAdapter<GetTableData, Adapter.ContactViewHolder>(DiffUtil()) {

    inner class ContactViewHolder(private val binding: CheckListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(getTableData: GetTableData) {
            binding.dateText.text = getTableData.date
//            binding.checkSwitch.isChecked = getTableData.check
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = CheckListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
        val checkSwitch: Switch = holder.itemView.findViewById(R.id.checkSwitch)
        checkSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            listener?.onItemCheck(holder.itemView, getItem(position))
        }
    }

    interface OnCheckedChangeListener {
        fun onItemCheck(v: View, item: GetTableData)
    }

    private var listener : OnCheckedChangeListener? = null

    fun setOnCheckedChangeListener(listener : OnCheckedChangeListener) {
        this.listener = listener
    }

}