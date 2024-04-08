package com.emines_employee.model

import java.io.Serializable

data class MyActivityLogsModel(
    var id:Int=0,
    var activityType:String?=null,
    var activityParty:String?=null,
    var activityPartyName:String?=null,
    var activityDate:String?=null,
    var activityTime:String?=null,
    var activityStatus:String?=null,
    var activityRemark:String?=null,
):Serializable
