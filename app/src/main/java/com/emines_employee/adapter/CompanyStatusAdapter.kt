package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemCompanyStatusBinding
import com.emines_employee.model.CompanyStatus

class CompanyStatusAdapter(
    val list: MutableList<CompanyStatus>,
    val context: Context,
    private val listener: OnCheckCompanyStatusListener
) : RecyclerView.Adapter<CompanyStatusAdapter.CompanyStatusVM>() {
    private var itemPosition = -1

    class CompanyStatusVM(val b: ItemCompanyStatusBinding) : ViewHolder(b.root)

    interface OnCheckCompanyStatusListener {
        fun onCheck(title: String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyStatusVM {
        return CompanyStatusVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_company_status,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CompanyStatusVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemCSTitle.text = model.title
            rbItemCS.isChecked = itemPosition == position
            rlItemCompanyStatus.setOnClickListener {
                itemPosition = position
                listener.onCheck(model.title.toString())
                notifyDataSetChanged()
            }


        }
    }

}