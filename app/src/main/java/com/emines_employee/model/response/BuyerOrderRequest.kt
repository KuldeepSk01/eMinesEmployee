package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BuyerOrderRequest(
    @SerializedName("emp_id")
    var empId: Int=-1,
    @SerializedName("buyer_id")
    var buyerId: Int=-1,
    @SerializedName("delivery_address")
    var deliveryAddressId: Int=-1,
    @SerializedName("orders")
    var orders: List<Orders> = mutableListOf()
):Serializable