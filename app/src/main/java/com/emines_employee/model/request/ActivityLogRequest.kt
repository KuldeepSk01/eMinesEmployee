package com.emines_employee.model.request

import com.google.gson.annotations.SerializedName

data class ActivityLogRequest(
    @SerializedName("employee_id")
    var employeeId:Int?=-1,
    @SerializedName("activity_type")
    var activityType:String?=null,
    @SerializedName("party_type")
    var partyType:String?=null,
    @SerializedName("party_id")
    var partyId:Int?=null,
    @SerializedName("party_name")
    var partyName:String?=null,
    @SerializedName("date")
    var date:String?=null,
    @SerializedName("time")
    var time:String?=null,
    @SerializedName("status")
    var status:String?=null,
    @SerializedName("remark")
    var remark:String?=null,
)
