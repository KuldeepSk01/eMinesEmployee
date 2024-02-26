package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemOnboardingLayoutBinding
import com.emines_employee.model.CommonModel

class OnBoardingAdapter(private val list: MutableList<CommonModel>, private val context: Context) :
    RecyclerView.Adapter<OnBoardingAdapter.OnVM>() {
    inner class OnVM(val b: ItemOnboardingLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnVM {
        val view = DataBindingUtil.inflate<ItemOnboardingLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_onboarding_layout, parent, false
        )

        return OnVM(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OnVM, position: Int) {
        val model = list[position]
        holder.b.ivOnboardingItem.setBackgroundResource(model.imgUrl!!)
        holder.b.tvOnboardingSubTitle.text = model.pageTitle
    }

}