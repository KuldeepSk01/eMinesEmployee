package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SellerOrderRequest(
    @SerializedName("emp_id")
    var empId: Int=-1,
    @SerializedName("seller_id")
    var sellerId: Int=-1,
    @SerializedName("delivery_address")
    var deliveryAddressId: Int=-1,
    @SerializedName("orders")
    var orders: List<Orders> = mutableListOf()
):Serializable