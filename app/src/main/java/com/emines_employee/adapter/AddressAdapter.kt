package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.AdapterAddressListener
import com.emines_employee.databinding.ItemAddressLayoutBinding
import com.emines_employee.model.UserAddress

class AddressAdapter(
    val list: MutableList<UserAddress>,
    val context: Context,
    private val listener: AdapterAddressListener
) : RecyclerView.Adapter<AddressAdapter.AddressVM>() {
    private var itemPosition = -1

    class AddressVM(val b: ItemAddressLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressVM {
        return AddressVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_address_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AddressVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemAddressType.text = model.type
            tvItemAddresses.text = model.address
            tvItemAddressTitle.text = model.city
            tvEditBtn.setOnClickListener {
                listener.onEditAddress(model)
            }

            rbItemAddress.isChecked = itemPosition == position
            rbItemAddress.setOnClickListener {
                itemPosition = position
                listener.onAddressSelect(model)
                notifyDataSetChanged()
            }


        }
    }

}