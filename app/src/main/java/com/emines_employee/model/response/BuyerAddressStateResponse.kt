package com.emines_employee.model.response

data class BuyerAddressStateResponse(
    val add_user: String,
    val adddate: String,
    val country_id: Int,
    val displayflag: String,
    val editdate: String,
    val id: Int,
    val state_id: Int,
    val state_slug: String,
    val statename: String,
)