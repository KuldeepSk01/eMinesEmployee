package com.emines_employee.model.response.target

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TargetDetailResponse(
    @SerializedName("month")
    val month: String,
    @SerializedName("volume")
    val volume: String,
    @SerializedName("achived_volume")
    val achivedVolume: String,
    @SerializedName("achived_volume_per")
    val achivedVolumePer: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("achived_order")
    val achivedOrder: String,
    @SerializedName("achived_order_per")
    val achivedOrderPer: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("achived_weight")
    val achivedWeight: String,
    @SerializedName("achived_weight_per")
    val achivedWeightPer: String,
):Serializable