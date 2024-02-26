package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnItemClickOrderListener
import com.emines_employee.databinding.ItemOrderLayoutBinding
import com.emines_employee.model.Order

class OrderAdapter(
    private val list: MutableList<Order>,
    val context: Context,
    private val listener: OnItemClickOrderListener
) : RecyclerView.Adapter<OrderAdapter.OrderVM>() {

    inner class OrderVM(val b: ItemOrderLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderVM {
        return OrderVM(
            DataBindingUtil.inflate<ItemOrderLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_order_layout,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: OrderVM, position: Int) {
        val model = list[position]

        holder.b.apply {
            tvItemOrderId.text = model.id.toString()
            tvType.text = model.orderType
            tvRateItemOrder.text = model.rate
            tvEstPriceItemOrder.text = model.estPrice.toString()
            tvDateCreateItemOrder.text = model.date
            tvPOStatusItemOrderBReq.text = model.status
            tvPOStatusItemOrderBReq.apply {
                when (model.status) {
                    context.resources.getString(R.string.in_process) -> {
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.order_picked_color,
                                null
                            )
                        )
                    }

                    context.resources.getString(R.string.cancelled) -> {
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.red_alert_color,
                                null
                            )
                        )
                    }

                    /*context.resources.getString(R.string.pending_text) -> {
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.order_pending_color,
                                null
                            )
                        )
                    }

                    context.resources.getString(R.string.placed_text) -> {
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.order_placed_color,
                                null
                            )
                        )
                    }*/

                    context.resources.getString(R.string.completed_text) -> {
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.blue_color_2,
                                null
                            )
                        )
                    }
                }
            }
            cvItemOrder.setOnClickListener {
                listener.onItemOrderClick(model)
            }
        }
    }

    override fun getItemCount() = list.size

}