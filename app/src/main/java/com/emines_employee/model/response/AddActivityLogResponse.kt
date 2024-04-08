package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName

data class AddActivityLogResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)