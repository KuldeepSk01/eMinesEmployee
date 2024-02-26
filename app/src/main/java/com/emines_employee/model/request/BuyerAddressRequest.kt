package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BuyerAddressRequest(
    @SerializedName("address")
    var address: String?=null,
    @SerializedName("address_type")
    var addressType: String?=null,
    @SerializedName("area")
    var area: String?=null,
    @SerializedName("buyer_id")
    var buyerId: Int=-1,
    @SerializedName("city")
    var city: String?=null,
    @SerializedName("country")
    var country: String?=null,
    @SerializedName("pincode")
    var pincode: String?=null,
    @SerializedName("state")
    var state: String?=null,
    @SerializedName("buyer_address_id")
    var buyerAddressId: Int?=null,
): Serializable