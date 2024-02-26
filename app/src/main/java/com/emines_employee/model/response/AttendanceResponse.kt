package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AttendanceResponse<T>(
    @SerializedName("absent")
    val absent: Int,
    @SerializedName("data")
    val data: List<T>,
    @SerializedName("message")
    val message: String,
    @SerializedName("present")
    val present: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("working_days")
    val working_days: Int
):Serializable





