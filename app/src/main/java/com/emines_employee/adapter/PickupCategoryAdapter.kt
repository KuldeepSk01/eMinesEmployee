package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnSellCategoryClickListener
import com.emines_employee.databinding.ItemSellCategoryLayoutBinding
import com.emines_employee.model.SellCategory

class PickupCategoryAdapter(
    val list: MutableList<SellCategory>,
    val context: Context,
    val listener: OnSellCategoryClickListener
) : RecyclerView.Adapter<PickupCategoryAdapter.SellCategoryVM>() {
    private var itemPosition = -1

    inner class SellCategoryVM(val b: ItemSellCategoryLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellCategoryVM {
        return SellCategoryVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sell_category_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SellCategoryVM, position: Int) {
        val category = list[position]
        holder.b.apply {
            if (itemPosition == position) {
                clFilterIcon.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.category_selection_drawable,
                    null
                )
            } else {
                clFilterIcon.background = null
            }
            ivItemFilterCategory.setBackgroundResource(category.img!!)
            tvItemFilterCategory.text = category.title
            clItemSellFilter.setOnClickListener {
                itemPosition = position
                // listener.onCategoryClick(category)
                notifyDataSetChanged()
            }
        }
    }


}