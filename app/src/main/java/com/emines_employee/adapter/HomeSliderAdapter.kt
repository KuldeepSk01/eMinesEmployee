package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemHomeSliderLayou1tBinding

class HomeSliderAdapter(private val list: MutableList<String>, val context: Context) :
    RecyclerView.Adapter<HomeSliderAdapter.HomeSliderVM>() {
    inner class HomeSliderVM(val b: ItemHomeSliderLayou1tBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSliderVM {
        return HomeSliderVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home_slider_layou1t,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeSliderVM, position: Int) {
        val model = list[position]
        holder.b.tvTitleHomeSlider.text = model.toString()
    }

}