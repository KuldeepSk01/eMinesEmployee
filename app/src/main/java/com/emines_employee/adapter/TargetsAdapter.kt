package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnTargetClickListener
import com.emines_employee.databinding.ItemTargetsLayoutBinding
import com.emines_employee.model.Targets
import com.emines_employee.model.response.target.Quarters

class TargetsAdapter(
    val list: MutableList<Quarters>,
    val context: Context,
    private val listener: OnTargetClickListener
) : RecyclerView.Adapter<TargetsAdapter.TargetsVM>() {
    class TargetsVM(val b: ItemTargetsLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetsVM {
        return TargetsVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_targets_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TargetsVM, position: Int) {
        val m = list[position]
        holder.b.apply {
            tvQ1ItemTarget.text = String.format("%s %s","Q${position.plus(1)}", m.year)
            tvQ1ItemTargetPercent.text = String.format("%s%s",(if (m.volume_achived_percentage.isNullOrEmpty())"0" else m.volume_achived_percentage),"%")
            tvItemVolumeTargetValue.text = String.format("%s %s","Rs",(if (m.volume_target.isNullOrEmpty())"0" else m.volume_target))
            tvItemTargetAchievedValue.text = String.format("%s %s","Rs", (if (m.volume_achived.isNullOrEmpty())"0" else m.volume_achived))


            tvItemOrderTargetValue.text = String.format("%s",(if (m.order_target.isNullOrEmpty())"0" else m.order_target))
            tvItemOrderTargetAchievedValue.text = String.format("%s",(if (m.order_achived.isNullOrEmpty())"0" else m.order_achived))

            tvItemWeightTargetValue.text = String.format("%s %s",(if (m.weight_target.isNullOrEmpty())"0" else m.weight_target),"MT")
            tvItemWeightTargetAchievedValue.text = String.format("%s %s",(if (m.weight_achived.isNullOrEmpty())"0" else m.weight_achived),"MT")

            tvItemTargetDate.visibility=View.VISIBLE
            tvItemTargetDate.text = m.quarter_months
            clItemTarget.setOnClickListener {
                listener.onTargetCLick(m)
            }

        }
    }

}