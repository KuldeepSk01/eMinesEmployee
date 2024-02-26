package com.emines_employee.model

import java.io.Serializable

data class Order(
    var id: Long = -1,
    var orderType: String? = null,
    var weight: String? = null,
    var rate: String? = null,
    var estPrice:Long?=-1,
    var date:String?=null,
    var status:String?=null
): Serializable