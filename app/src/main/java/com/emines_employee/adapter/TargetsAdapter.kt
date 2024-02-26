package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnTargetClickListener
import com.emines_employee.databinding.ItemTargetsLayoutBinding
import com.emines_employee.model.Targets

class TargetsAdapter(
    val list: MutableList<Targets>,
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
            tvQ1ItemTarget.text = String.format("%s%s","Q${m.id}","-${m.quarterDate}")
            tvQ1ItemTargetPercent.text = String.format("%d%s",m.quarterPercentage,"%")
            tvItemTargetValue.text = String.format("%s %s",m.quarterTarget,"Rs")
            tvItemTargetAchievedValue.text = String.format("%d %s",m.quarterAchievedTarget,"Rs")
            tvItemTargetDate.text = m.quarterMonths
            clItemTarget.setOnClickListener {
                listener.onTargetCLick(m)
            }

        }
    }

}