package com.emines_employee.screen.dashboard.home.attendance

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.model.request.AttendanceSheetReq
import com.emines_employee.model.response.Attendance
import com.emines_employee.model.response.AttendanceResponse
import com.emines_employee.network.ApiResponse

class AttendanceViewModel(private val repo: AttendanceRepo) : BaseViewModel() {
    val attendanceResponse = MutableLiveData<ApiResponse<AttendanceResponse<Attendance>>>()

    fun hitAttendanceSheetApi(req: AttendanceSheetReq) {
        repo.executeAttendanceApi(req, attendanceResponse)
    }

}