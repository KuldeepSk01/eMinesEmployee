package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemTargetsLayoutBinding
import com.emines_employee.model.response.target.TargetDetailResponse

class TargetsDetailAdapter(
    val list: MutableList<TargetDetailResponse>,
    val context: Context
) : RecyclerView.Adapter<TargetsDetailAdapter.TargetsDetailVM>() {
    class TargetsDetailVM(val b: ItemTargetsLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetsDetailVM {
        return TargetsDetailVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_targets_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: TargetsDetailVM, position: Int) {
        val m = list[position]
        holder.b.apply {
           // tvQ1ItemTarget.text = String.format("%s %s","Q${position.plus(1)}", m.month)
            tvQ1ItemTargetPercent.text = String.format("%s%s",(if(m.achivedVolumePer.isNullOrEmpty())"0" else m.achivedVolumePer),"%" )
            tvItemVolumeTargetValue.text = String.format("%s %s","Rs",(if(m.volume.isNullOrEmpty())"0" else m.volume))
            tvItemTargetAchievedValue.text = String.format("%s %s","Rs",(if(m.achivedVolume.isNullOrEmpty())"0" else m.achivedVolume))

            tvItemOrderTargetValue.text = String.format("%s",(if(m.order.isNullOrEmpty())"0" else m.order))
            tvItemOrderTargetAchievedValue.text = String.format("%s",(if(m.achivedOrder.isNullOrEmpty())"0" else m.achivedOrder))

            tvItemWeightTargetValue.text = String.format("%s %s",(if(m.weight.isNullOrEmpty())"0" else m.weight),"MT")
            tvItemWeightTargetAchievedValue.text = String.format("%s %s",(if(m.achivedWeight.isNullOrEmpty())"0" else m.achivedWeight),"MT")

            tvQ1ItemTarget.text = m.month


        }
    }

}