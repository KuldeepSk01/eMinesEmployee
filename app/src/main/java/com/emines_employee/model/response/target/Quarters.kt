package com.emines_employee.model.response.target

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Quarters(
    @SerializedName("order_achived")
    val order_achived: String,
    @SerializedName("order_achived_percentage")
    val order_achived_percentage: String,
    @SerializedName("order_target")
    val order_target: String,
    @SerializedName("quarter_months")
    val quarter_months: String,
    @SerializedName("volume_achived")
    val volume_achived: String,
    @SerializedName("volume_achived_percentage")
    val volume_achived_percentage: String,
    @SerializedName("volume_target")
    val volume_target: String,
    @SerializedName("weight_achived")
    val weight_achived: String,
    @SerializedName("weight_achived_percentage")
    val weight_achived_percentage: String,
    @SerializedName("weight_target")
    val weight_target: String,
    @SerializedName("year")
    val year: String,
    var quarterName:String=""
):Serializable