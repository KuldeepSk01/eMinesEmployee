package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnSellCategoryClickListener
import com.emines_employee.databinding.ItemSellCategoryLayoutBinding
import com.emines_employee.model.SellCategory
import com.emines_employee.model.response.CategoryResponse

class SellCategoryAdapter(
    val list: MutableList<CategoryResponse>,
    val context: Context,
    val listener: OnSellCategoryClickListener
) : RecyclerView.Adapter<SellCategoryAdapter.SellCategoryVM>() {
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
                // tvItemFilterTermCode.visibility= View.VISIBLE
                tvItemFilterCategory.setTextColor(ResourcesCompat.getColor(context.resources,R.color.primary_color,null))
                clFilterIcon.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.category_selection_drawable,
                    null
                )
            } else {
                //  tvItemFilterTermCode.visibility= View.GONE
                tvItemFilterCategory.setTextColor(ResourcesCompat.getColor(context.resources,R.color.default_text_color,null))
                clFilterIcon.background = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.category_unselection_drawable,
                    null
                )            }

            Glide.with(context).load(category.thumbImage).placeholder(R.drawable.refregerator).into(ivItemFilterCategory)
            tvItemFilterCategory.text = category.categoryname
            // tvItemFilterTermCode.text = category.termsCode
            clItemSellFilter.setOnClickListener {
                itemPosition = position
                listener.onCategoryClick(category)
                notifyDataSetChanged()
            }
        }
    }


}