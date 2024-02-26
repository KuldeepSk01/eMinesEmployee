package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TargetsResponse(
    @SerializedName("achived_quarter_four_target")
    val achievedQuarterFourTarget: Int,
    @SerializedName("achived_quarter_four_target_parcentage")
    val achievedQuarterFourTargetParcentage: Int,
    @SerializedName("achived_quarter_one_target")
    val achievedQuarterOneTarget: Int,
    @SerializedName("achived_quarter_one_target_parcentage")
    val achievedQuarterOneTargetParcentage: Int,
    @SerializedName("achived_quarter_three_target")
    val achievedQuarterThreeTarget: Int,
    @SerializedName("achived_quarter_three_target_parcentage")
    val achievedQuarterThreeTargetParcentage: Int,
    @SerializedName("achived_quarter_two_target")
    val achievedQuarterTwoTarget: Int,
    @SerializedName("achived_quarter_two_target_parcentage")
    val achievedQuarterTwoTargetParcentage: Int,
    @SerializedName("quarter_four_target")
    val quarterFourTarget: String,
    @SerializedName("quarter_one_target")
    val quarterOneTarget: String,
    @SerializedName("quarter_three_target")
    val quarterThreeTarget: String,
    @SerializedName("quarter_two_target")
    val quarterTwoTarget: String
):Serializable