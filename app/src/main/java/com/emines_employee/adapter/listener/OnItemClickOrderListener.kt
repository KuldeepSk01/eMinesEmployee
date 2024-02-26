package com.emines_employee.adapter.listener

import com.emines_employee.model.Order
import com.emines_employee.model.response.RequestOrderResponse

interface OnItemClickOrderListener {
    fun onItemOrderClick(order: Order)
    fun onClickBuyingRequest(order: RequestOrderResponse)
    fun onClickPurchaseOrder(order: RequestOrderResponse)
}