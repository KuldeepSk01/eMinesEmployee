package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Orders(
    @SerializedName("product_id")
    var productId: Int=-1,
    @SerializedName("purchesed_qty")
    var purchesedQty: Int=-1,
    @SerializedName("unit_of_quantity")
    var unitOfQuantity: String?=null,
    @SerializedName("unit_rate")
    var unitRate: Int=-1
):Serializable