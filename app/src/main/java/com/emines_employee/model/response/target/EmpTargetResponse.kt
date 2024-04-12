package com.emines_employee.model.response.target

import com.emines_employee.model.response.target.Quarters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EmpTargetResponse(
    @SerializedName("quarter_four")
    val quarter_four: Quarters,
    @SerializedName("quarter_one")
    val quarter_one: Quarters,
    @SerializedName("quarter_three")
    val quarter_three: Quarters,
    @SerializedName("quarter_two")
    val quarter_two: Quarters,
    @SerializedName("year")
    val year: String
): Serializable