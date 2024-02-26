package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AttendanceSheetReq(
    @SerializedName("user_id")
    var userId:Int=-1,
    @SerializedName("type")
    var type:String?=null,
    @SerializedName("month")
    var month:String?=null,
    @SerializedName("year")
    var year:String?=null,


):Serializable