package com.emines_employee.adapter.listener

import com.emines_employee.model.UserAddress

interface AdapterAddressListener {
    fun onAddressSelect(address:UserAddress)
    fun onEditAddress(address:UserAddress)
}