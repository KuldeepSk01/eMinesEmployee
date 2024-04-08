package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.AdapterBuyerAddressListener
import com.emines_employee.databinding.ItemAddressLayoutBinding
import com.emines_employee.databinding.ItemByuerSellerAddressLayoutBinding
import com.emines_employee.model.response.BuyerAddressResponse

class BuyerAddressAdapter(
    val list: MutableList<BuyerAddressResponse>,
    val context: Context,
    private val listener: AdapterBuyerAddressListener
) : RecyclerView.Adapter<BuyerAddressAdapter.AddressVM>() {
    private var itemPosition = -1

    class AddressVM(val b: ItemByuerSellerAddressLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressVM {
        return AddressVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_byuer_seller_address_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AddressVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemAddressType.text = model.address_type
            tvItemAddress1.text = model.address
            tvItemCity.text = model.city
            tvItemState.text = model.state_mame
            tvItemCountry.text = model.country_name
            tvItemPinCode.text = model.pincode

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