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

class PopupItemPickAdapter(
    val list: MutableList<BuyersResponse>,
    val context: Context,
    private val listener: OnScheduledTimePickerListener
) :
    RecyclerView.Adapter<PopupItemPickAdapter.ScheduledTimePickVM>() {
    class ScheduledTimePickVM(val b: ItemPopupListBinding) : ViewHolder(b.root)

    interface OnScheduledTimePickerListener {
        fun onTimePicker(model: BuyersResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledTimePickVM {
        return ScheduledTimePickVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_popup_list, parent, false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ScheduledTimePickVM, position: Int) {
        val model = list[position]
        holder.b.apply {
           /* if (position%2==0){
                rlItemPopup.setBackgroundColor(context.getColor(R.color.box_color_1))

            }else{
                rlItemPopup.setBackgroundColor(context.getColor(R.color.box_color_2))
            }*/
            tvItemScheduleTime.text = model.name
            tvItemScheduleTime.setOnClickListener {
                listener.onTimePicker(model)
            }
        }
    }
}