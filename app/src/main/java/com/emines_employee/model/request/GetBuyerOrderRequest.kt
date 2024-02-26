package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetBuyerOrderRequest(
    @SerializedName("buyer_id")
    var buyerId: Int=-1,
    @SerializedName("order_status")
    var orderStatus: String?=null,
    @SerializedName("user_id")
    var userId: Int=-1
):Serializable