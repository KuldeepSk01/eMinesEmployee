package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActivityLogResponse(
    @SerializedName("activity_type")
    val activityType: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("employee_id")
    val employeeId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("party_id")
    val partyId: String,
    @SerializedName("party_name")
    val partyName: String,
    @SerializedName("party_type")
    val partyType: String,
    @SerializedName("remark")
    val remark: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("time")
    val time: String
):Serializable