package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnClickBuyerOrderListener
import com.emines_employee.adapter.listener.OnItemClickOrderListener
import com.emines_employee.databinding.ItemOrderByuingRequsetLayoutBinding
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.util.formatDate

class OrderBuyingReqAdapter(
    private val list: MutableList<RequestOrderResponse>,
    val context: Context,
    private val listener: OnClickBuyerOrderListener
) : RecyclerView.Adapter<OrderBuyingReqAdapter.OrderBuyingRequestVM>() {

    inner class OrderBuyingRequestVM(val b: ItemOrderByuingRequsetLayoutBinding) :
        ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBuyingRequestVM {
        return OrderBuyingRequestVM(
            DataBindingUtil.inflate<ItemOrderByuingRequsetLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_order_byuing_requset_layout,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: OrderBuyingRequestVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvItemOrderBReqId.text = String.format(" %s%s",context.getString(R.string.defaultID),model.id.toString())
            tvType.text = model.total_order_category.toString()
            tvWeight.text =  String.format("%s %s",model.total_purchased_quantity.toString(),"MT")
            //String.format("%s %s",DecimalFormat("#.#").format(it.rating).toString()
            tvRateItemOrderBReq.text = String.format("%s %s",context.getString(R.string.indian_rupee_symbol),model.total_amount_without_gst.toString())
            tvItemOrderBReqStatus.text = model.status
            tvDateItemOrderBReq.text = formatDate(model.created_at.toString(), "yyyy-MM-dd'T'HH:mm:ss", "dd MMM yyyy")


            tvItemOrderBReqStatus.apply {
                when(model.status){
                    context.getString(R.string.new_request)->{
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.blue_color_2,
                                null
                            )
                        )
                    }
                    context.getString(R.string.approved)->{
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.order_complete_color,
                                null
                            )
                        )
                    }
                    context.getString(R.string.cancelled)->{
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.red_alert_color,
                                null
                            )
                        )
                    }
                }
            }
            cvItemOrderBReq.setOnClickListener {
                listener.onClickBuyingRequest(model)
            }

        }
    }

    override fun getItemCount() = list.size

}