package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetSellerOrderRequest(
    @SerializedName("seller_id")
    var sellerId: Int=-1,
    @SerializedName("order_status")
    var orderStatus: String?=null,
    @SerializedName("user_id")
    var userId: Int=-1
):Serializable