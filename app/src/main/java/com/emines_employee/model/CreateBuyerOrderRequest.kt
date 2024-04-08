package com.emines_employee.model

import com.emines_employee.model.response.BuyersResponse
import java.io.Serializable

data class CreateBuyerOrderRequest(
    var goodsList: List<BuyerGoods> = mutableListOf(),
    var buyer: BuyersResponse? = null,
    var seller: BuyersResponse? = null
) : Serializable


data class BuyerGoods(
    var id: Int = -1,
    var descriptionOdGoods: String? = null,
    var purchaseQuantity: String? = null,
    var termsCode: String? = null,
    var unitOfQuantity: String? = null,
    var rate: String? = null,
    var totalAmount: String? = null,
    var gst: String? = null,
    var totalAmountWithGst: String? = null
) : Serializable
