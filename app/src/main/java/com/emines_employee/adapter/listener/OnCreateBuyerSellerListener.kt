package com.emines_employee.adapter.listener

import com.emines_employee.model.User
import com.emines_employee.model.response.BuyersResponse

interface OnCreateBuyerSellerListener {
    fun onUpdate(model: BuyersResponse)
    fun onCreateClick(model: BuyersResponse)
    fun onViewClick(model: BuyersResponse)
    fun onMapClick(model: BuyersResponse)
    fun onCallClick(model: BuyersResponse)




    fun onClickBuyingRequest(model: BuyersResponse)
    fun onClickPurchaseOrder(model: BuyersResponse)
    fun onClickTotalOrder(model: BuyersResponse)
}