package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemBuyerOrderReqGoodsLayoutBinding
import com.emines_employee.model.BuyerGoods

class AddBuyerGoodsAdapter(
    val list: MutableList<BuyerGoods>,
    val context: Context
) : RecyclerView.Adapter<AddBuyerGoodsAdapter.CompanyStatusVM>() {
    class CompanyStatusVM(val b: ItemBuyerOrderReqGoodsLayoutBinding) : ViewHolder(b.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyStatusVM {
        return CompanyStatusVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_buyer_order_req_goods_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CompanyStatusVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvGoodsItemName.text =
                String.format("%s %s", model.descriptionOdGoods, " (${model.termsCode})")
            tvGoodsItemPurchaseQuantity.text = model.purchaseQuantity
            tvGoodsItemRatePerKg.text = model.rate
            tvGoodsItemUnitOfQuantity.text = model.unitOfQuantity
            tvGoodsItemTotalAmount.text = String.format("%s %s",context.getString(R.string.indian_rupee_symbol),model.totalAmount)
            tvGoodsItemGst.text = model.gst
            tvGoodsItemTotalAmountWithGST.text =String.format("%s %s",context.getString(R.string.indian_rupee_symbol),model.totalAmountWithGst)
        }
    }

}