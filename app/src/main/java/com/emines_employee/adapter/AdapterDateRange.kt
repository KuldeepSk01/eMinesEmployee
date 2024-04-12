package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemPopupListBinding
import com.emines_employee.model.response.BuyersResponse

class AdapterDateRange(
    val list: MutableList<String>,
    val context: Context,
    private val listener: OnDateRangePickListener
) :
    RecyclerView.Adapter<AdapterDateRange.DateRangeVM>() {
    class DateRangeVM(val b: ItemPopupListBinding) : ViewHolder(b.root)

    interface OnDateRangePickListener {
        fun onTimePicker(model: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateRangeVM {
        return DateRangeVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_popup_list, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: DateRangeVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemScheduleTime.text = model
            tvItemScheduleTime.setOnClickListener {
                listener.onTimePicker(model)
            }
        }
    }
}