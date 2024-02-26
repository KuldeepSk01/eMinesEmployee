package com.emines_employee.adapter.listener

import com.emines_employee.model.response.Attendance

interface AdepterAttendanceListener {
    fun onClickMonthAttendance(model:Attendance)
    fun onClickYearAttendance(model:Attendance)
}