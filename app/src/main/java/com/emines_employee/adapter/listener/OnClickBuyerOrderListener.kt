package com.emines_employee.adapter.listener

import com.emines_employee.model.Order
import com.emines_employee.model.response.RequestOrderResponse

interface OnClickBuyerOrderListener {
    fun onClickBuyingRequest(order: RequestOrderResponse)
    //fun onClickPurchaseOrder(order: RequestOrderResponse)
    //fun onClickPickupOrder(order: RequestOrderResponse)
    fun onClickInProcessOrder(order: RequestOrderResponse)
    fun onClickDeliveredOrder(order: RequestOrderResponse)
}