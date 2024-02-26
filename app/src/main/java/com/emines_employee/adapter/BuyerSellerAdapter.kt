package com.emines_employee.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnCreateBuyerSellerListener
import com.emines_employee.databinding.ItemBuyerLayoutBinding
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.util.formatDate

class BuyerSellerAdapter(
    val list: MutableList<BuyersResponse>, val context: Context,
    private val listener: OnCreateBuyerSellerListener
) :
    RecyclerView.Adapter<BuyerSellerAdapter.BuyerSellerVM>() {
    class BuyerSellerVM(val b: ItemBuyerLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyerSellerVM {
        return BuyerSellerVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_buyer_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BuyerSellerVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load(model.profile_pic).placeholder(R.drawable.greenscap)
                .into(ivItemBuyer)
            tvTitleItemBuyer.text = model.company_name
            tvItemBuyerName.text = model.name
            tvTypeBuyerSellerItemBuyer.text = model.buyer_type
            tvAddressItemBuyer.text = model.financial_address
            tvItemBuyerDateOfA.text = String.format("%s %s","Add :",
                formatDate(model.created_at, "yyyy-MM-dd'T'HH:mm:ss", "dd MMM yyyy")
            )



            tvUpdateBtnItemBuyer.setOnClickListener {
                listener.onUpdate(model)
            }
            tvCreatePickupBtnItemBuyer.setOnClickListener {
                listener.onCreateClick(model)
            }

            tvViewBtnItemBuyer.setOnClickListener {
                listener.onViewClick(model)
            }
            tvCallBtnItemBuyer.setOnClickListener {
                listener.onCallClick(model)
            }
            tvGoogleMapBtnItemBuyer.setOnClickListener {
                listener.onMapClick(model)
            }


//            tvItemBuyerEnquiry.setOnClickListener {
//                listener.onClickEnquiry(model)
//            }

            tvItemBuyerBuyingReq.apply {
                text = String.format(
                    "%s%s",
                    context.getString(R.string.buying_req),
                    "(${model.buying_requests.toString()})"
                )

                setOnClickListener {
                    listener.onClickBuyingRequest(model)
                }
            }

            tvItemBuyerPO.apply {
                text = String.format(
                    "%s%s",
                    context.getString(R.string.po),
                    "(${model.purchased_orders.toString()})"
                )
                setOnClickListener {
                    listener.onClickPurchaseOrder(model)
                }
            }

            tvItemBuyerTotalNoOrder.apply {
                text = String.format(
                    "%s%s",
                    context.getString(R.string.total_order),
                    "(${model.total_orders.toString()})"
                )
                setOnClickListener {
                    listener.onClickTotalOrder(model)
                }

            }


        }
    }


}