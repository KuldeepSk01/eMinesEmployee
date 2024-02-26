package com.emines_employee.adapter.listener

import com.emines_employee.model.UserAddress
import com.emines_employee.model.response.BuyerAddressResponse

interface AdapterBuyerAddressListener {
    fun onAddressSelect(address:BuyerAddressResponse)
    fun onEditAddress(address:BuyerAddressResponse)
}