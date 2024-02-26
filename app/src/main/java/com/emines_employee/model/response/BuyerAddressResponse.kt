package com.emines_employee.model.response

import java.io.Serializable

data class BuyerAddressResponse(
    val address: String,
    val address_type: String,
    val area: String,
    val buyer_id: Int,
    val city: String,
    val country: String,
    val country_name: String,
    val created_at: String,
    val id: Int,
    val pincode: String,
    val state: String,
    val state_mame: String,
    val updated_at: String
):Serializable