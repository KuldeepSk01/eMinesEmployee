package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApplyLeaveReq(
    @SerializedName("user_id")
    var userId:Int=-1,
    @SerializedName("leave_type")
    var leaveType:String?=null,
    @SerializedName("day_type")
    var dayType:String?=null,
    @SerializedName("leave_dates")
    var leaveDates:List<String> = mutableListOf(),
    @SerializedName("is_multi_day")
    var isMultiDay:Int=-1
):Serializable