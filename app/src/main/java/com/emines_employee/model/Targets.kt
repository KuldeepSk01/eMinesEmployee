package com.emines_employee.model

import java.io.Serializable

data class Targets(
    val order_achived: String,
    val order_achived_percentage: String,
    val order_target: String,
    val quarter_months: String,
    val volume_achived: String,
    val volume_achived_percentage: String,
    val volume_target: String,
    val weight_achived: String,
    val weight_achived_percentage: String,
    val weight_target: String
):Serializable
