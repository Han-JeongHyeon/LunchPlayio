package com.example.flow.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flow.Data.GetTableData
import com.example.flow.R
import com.example.flow.databinding.CheckListBinding

class Adapter :
    ListAdapter<GetTableData, Adapter.ContactViewHolder>(DiffUtil()) {

    inner class ContactViewHolder(private val binding: CheckListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(getTableData: GetTableData) {
            val available = getTableData.is_availiable == "0"
            val checked = getTableData.is_checked == "1"

            binding.dateText.text = getTableData.date

            binding.switchLayout.isGone = available
            binding.checkSwitch.isChecked = checked

            binding.deadLine.isGone = !available

            if (available) {
                binding.deadLine.text = "주문 마감\n(내 주문: ${if(checked) "주문" else "미주문"})"

                binding.checkListLayout.setBackgroundResource(
                    if (getTableData.cnt.toInt() >= 10) R.drawable.default_background else R.drawable.deadline_background
                )
            }

            binding.orderCount.text = getTableData.cnt
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
            listener?.onItemCheck(holder.itemView, getItem(position), isChecked)
        }
    }

    interface OnCheckedChangeListener {
        fun onItemCheck(v: View, item: GetTableData, isChecked: Boolean)
    }

    private var listener: OnCheckedChangeListener? = null

    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        this.listener = listener
    }

}