package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserResponse(
    @SerializedName("aadhar_file")
    val aadharFile: String,
    @SerializedName("aadhar_no")
    val aadharNo: String,
    @SerializedName("account_name")
    val accountName: String,
    @SerializedName("account_number")
    val accountNumber: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("branch_name")
    val branchName: String,
    @SerializedName("cancle_cheque")
    val cancleCheque: String,
    @SerializedName("cancle_cheque_file")
    val cancleChequeFile: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_address")
    val currentAddress: String,
    @SerializedName("current_area")
    val currentArea: String,
    @SerializedName("current_city")
    val currentCity: String,
    @SerializedName("current_city_name")
    val currentCityName: String,
    @SerializedName("current_country")
    val currentCountry: String,
    @SerializedName("current_country_name")
    val currentCountryName: String,
    @SerializedName("current_pin")
    val currentPin: String,
    @SerializedName("current_state")
    val currentState: String,
    @SerializedName("current_state_name")
    val currentStateName: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("designation")
    val designation: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("ifsc_code")
    val ifscCode: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pan_file")
    val panFile: String,
    @SerializedName("pan_no")
    val panNo: String,
    @SerializedName("permanent_address")
    val permanentAddress: String,
    @SerializedName("permanent_area")
    val permanentArea: String,
    @SerializedName("permanent_city")
    val permanentCity: String,
    @SerializedName("permanent_city_name")
    val permanentCityName: String,
    @SerializedName("permanent_country")
    val permanentCountry: String,
    @SerializedName("permanent_country_name")
    val permanentCountryName: String,
    @SerializedName("permanent_pin")
    val permanentPin: String,
    @SerializedName("permanent_state")
    val permanentState: String,
    @SerializedName("permanent_state_name")
    val permanentStateName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("profile_pic")
    val profilePic: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("worked_as_a")
    val workedAsA: String,
    @SerializedName("is_present")
    val isPresent: String
):Serializable