package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemCustomListLayoutBinding
import com.emines_employee.model.response.BuyerAddressStateResponse

class CustomTextPickerAdapter(
    val list: MutableList<BuyerAddressStateResponse>,
    val context: Context,
    private val listener: OnStatePickerListener
) :
    RecyclerView.Adapter<CustomTextPickerAdapter.CustomTextPickerVM>() {
    class CustomTextPickerVM(val b: ItemCustomListLayoutBinding) : ViewHolder(b.root)

    interface OnStatePickerListener {
        fun onStatePicker(model: BuyerAddressStateResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomTextPickerVM {
        return CustomTextPickerVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_custom_list_layout, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CustomTextPickerVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvCustomItemText.text = model.statename
            tvCustomItemText.setOnClickListener {
                listener.onStatePicker(model)
            }
        }
    }
}