package com.emines_employee.model.response.target

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyTargetsResponse(
    @SerializedName("code")
    val code: Long,
    @SerializedName("data")
    val data: EmpTargetResponse,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean,
):Serializable