package com.emines_employee.model

import java.io.Serializable

data class Targets(
    var id:Long?=-1,
    var quarterPercentage:Int?=null,
    var quarterTarget:String?=null,
    var quarterAchievedTarget:Int?=null,
    var quarterDate:String?=null,
    var quarterMonths:String?=null,
):Serializable
