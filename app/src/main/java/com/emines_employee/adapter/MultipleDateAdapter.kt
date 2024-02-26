package com.emines_employee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.LayoutSelectDateBinding


class MultipleDateAdapter(val list: MutableList<String>) :
    RecyclerView.Adapter<MultipleDateAdapter.ScheduledTimePickVM>() {
    class ScheduledTimePickVM(val b: LayoutSelectDateBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledTimePickVM {
        return ScheduledTimePickVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.layout_select_date, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ScheduledTimePickVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemScheduleTime.text = model
        }
    }
}