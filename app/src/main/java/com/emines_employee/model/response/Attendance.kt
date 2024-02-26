package com.emines_employee.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Attendance(
    @SerializedName("dateValue")
    val dateValue: String?=null,
    @SerializedName("is_present")
    val isPresent: String?=null,
    @SerializedName("attendance_time")
    val attendanceTime: String?=null,
    @SerializedName("current_location")
    val currentLocation: String?=null,

    @SerializedName("month")
    val month: String?=null,
    @SerializedName("present")
    val present: Int?=-1,
    @SerializedName("leave")
    val leave: Int?=-1,
    @SerializedName("absent")
    val absent: Int?=-1,

    @SerializedName("attendance_count")
    val attendanceCount: Int,
    @SerializedName("days_in_year")
    val daysInYear: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("start_year")
    val startYear: String,
    @SerializedName("end_year")
    val endYear: String,
    @SerializedName("year")
    val year: String,


    ): Serializable