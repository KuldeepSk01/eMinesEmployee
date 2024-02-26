package com.emines_employee.screen.dashboard.home.attendance

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.model.request.AttendanceSheetReq
import com.emines_employee.model.response.Attendance
import com.emines_employee.model.response.AttendanceResponse
import com.emines_employee.model.response.LoginOtpResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendanceRepo : BaseRepository() {
    fun executeAttendanceApi(req:AttendanceSheetReq,responseLiveData: MutableLiveData<ApiResponse<AttendanceResponse<Attendance>>>){
        val call = apiService.attendanceSheet(req)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<AttendanceResponse<Attendance>> {
            override fun onResponse(
                call: Call<AttendanceResponse<Attendance>>,
                response: Response<AttendanceResponse<Attendance>>
            ) {
                try {
                    if (response.code()== Constants.NetworkConstant.API_SUCCESS){
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    }else{
                        val jObjErrorMessage = JSONObject(
                            response.errorBody()!!.string()
                        ).getString("message")
                        mLog( jObjErrorMessage)
                        responseLiveData.postValue(ApiResponse.error(Throwable(jObjErrorMessage)))
                    }
                }catch (e:Exception){
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(call: Call<AttendanceResponse<Attendance>>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("LoginRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }

}