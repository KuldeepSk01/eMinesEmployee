package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnClickBuyerOrderListener
import com.emines_employee.databinding.ItemOrderPoLayoutBinding
import com.emines_employee.model.response.RequestOrderResponse

class OrderPOAdapter(
    private val list: MutableList<RequestOrderResponse>,
    val context: Context,
    private val listener: OnClickBuyerOrderListener
) : RecyclerView.Adapter<OrderPOAdapter.OrderPurchaseVM>() {
    inner class OrderPurchaseVM(val b: ItemOrderPoLayoutBinding) : ViewHolder(b.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderPurchaseVM {
        return OrderPurchaseVM(
            DataBindingUtil.inflate<ItemOrderPoLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_order_po_layout,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: OrderPurchaseVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemPOId.text = String.format(" %s%s",
                context.getString(R.string.defaultID), model.id.toString())

            tvTotalCategoryPO.text = model.total_order_category.toString()
            tvTotalWeightPO.text = String.format("%s %s",model.total_purchased_quantity.toString(),"MT")

            tvPOAddress.text = model.destination.toString()
            tvPODropAddressItemPOBReq.text = model.address.toString()
            // tvra.text = model.total_purchased_quantity.toString()
            //tvEstPriceItemOrderBReq.text = model.po_no
            //tvOrderStatus.text = model.status
            tvItemPOAssignTransporter.text = model.assigned_to
            tvItemTotalPOCount.text = model.total_orders_againest_po.toString()

            tvItemDeliveryDate.text = if (model.delivery_date.isNullOrEmpty()) getFormattedDate(model.estimated_delivery_date) else getFormattedDate(model.delivery_date)
            tvItemPoNo.text = model.po_no

            tvOrderStatus.apply {
                when (model.status) {


                    context.getString(R.string.assigned) -> {
                        text = String.format("%s, %s", model.status, " ${getFormattedDate(model.assigned_date)}")
                        setTextColor(context.getColor(R.color.order_confirm_color))
                    }

                    context.getString(R.string.new_order) -> {
                        llcItemPOAssignTransporter.visibility = View.GONE
                        text = model.status
                        setTextColor(context.getColor(R.color.order_confirm_color))
                    }

                    context.getString(R.string.reached) -> {
                        text = String.format("%s, %s", model.status, " ${getFormattedDate(model.reached_date)}")
                        setTextColor(context.getColor(R.color.order_complete_color))

                    }

                    context.getString(R.string.accepted) -> {
                        text = String.format("%s, %s", model.status, " ${getFormattedDate(model.accepte_date)}")
                        setTextColor(context.getColor(R.color.order_complete_color))

                    }

                    context.getString(R.string.delivered) -> {
                        tvEstDate.text = "Delivered Date"
                        text = String.format("%s, %s", model.status, " ${getFormattedDate(model.delivery_date)}")
                        setTextColor(context.getColor(R.color.order_complete_color))
                        llcItemPOAssignTransporter.apply {
                            visibility = View.VISIBLE
                            tvItemPOAssignTransporter.text = model.assigned_to
                        }
                    }

                    context.getString(R.string.picked_up) -> {
                        text = String.format("%s, %s", model.status, " ${getFormattedDate(model.pickup_date)}")
                        setTextColor(context.getColor(R.color.order_picked_color))
                    }

                    context.getString(R.string.denied) -> {
                        text = String.format("%s, %s", model.status, " ${getFormattedDate(model.assigned_date)}")
                        setTextColor(context.getColor(R.color.red_alert_color))
                    }

                    context.getString(R.string.in_process) -> {
                        setTextColor(context.getColor(R.color.order_placed_color))
                    }
                    else->{
                        llcItemTotalPOCount.visibility = View.VISIBLE
                        llcItemPOAssignTransporter.visibility = View.GONE
                        text = String.format("%s, %s", model.status, " ${getFormattedDate(model.po_date!!)}")
                        setTextColor(context.getColor(R.color.order_confirm_color))
                    }
                }
            }

            cvItemPOBReq.setOnClickListener {
                when (model.status) {
                    context.getString(R.string.reached), context.getString(R.string.picked_up) -> {
                        listener.onClickInProcessOrder(model)
                    }
                    context.getString(R.string.delivered) -> {
                        listener.onClickDeliveredOrder(model)
                    }
                   /* context.getString(R.string.denied), context.getString(R.string.accepted),context.getString(R.string.assigned) -> {
                        listener.onClickPickupOrder(model)
                    }*/
                    else->{
                        listener.onClickBuyingRequest(model)
                    }

                }

            }

        }
    }

    override fun getItemCount() = list.size


    private fun getFormattedDate(date:String):String{
        return date
        // return formatDate(date,"dd-MM-yyyy","dd MMM yyy")
    }
}