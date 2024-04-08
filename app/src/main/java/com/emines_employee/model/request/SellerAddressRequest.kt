package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SellerAddressRequest(
    @SerializedName("address")
    var address: String?=null,
    @SerializedName("address_type")
    var addressType: String?=null,
    @SerializedName("area")
    var area: String?=null,
    @SerializedName("seller_id")
    var sellerId: Int=-1,
    @SerializedName("city")
    var city: String?=null,
    @SerializedName("country")
    var country: String?=null,
    @SerializedName("pincode")
    var pincode: String?=null,
    @SerializedName("state")
    var state: String?=null,
    @SerializedName("seller_address_id")
    var sellerAddressId: Int?=null,
): Serializable