package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MarkPresentRepo(
    @SerializedName("user_id")
    var userId:Int?=null,
    @SerializedName("current_location")
    var currentLocation:String?=null,
    @SerializedName("is_half_day")
    var isHalfDay:Int=0
):Serializable
