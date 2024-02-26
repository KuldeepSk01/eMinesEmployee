package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DashboardResponse(
    @SerializedName("total_orders")
    val total_orders: Int,
    @SerializedName("buying_requests")
    val buying_requests: Int,
    @SerializedName("assigned_requests")
    val assigned_requests: Int,
    @SerializedName("approved_requests")
    val approved_requests: Int,
    @SerializedName("cancelled_requests")
    val cancelled_requests: Int,
    @SerializedName("po_recieved_requests")
    val po_recieved_requests: Int,
    @SerializedName("denied_orders")
    val denied_orders: Int,
    @SerializedName("pickupOrders")
    val pickupOrders: Int,
    @SerializedName("inprocessOrders")
    val inprocessOrders: Int,
    @SerializedName("deliveredOrders")
    val deliveredOrders: Int,
    @SerializedName("buyers")
    val buyers: Int,
    @SerializedName("sellers")
    val sellers: Int,
):Serializable