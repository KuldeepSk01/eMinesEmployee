package com.emines_employee.screen.verifyme2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.BaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.ApplyLeaveReq
import com.emines_employee.model.request.MarkPresentRepo
import com.emines_employee.model.response.LoginOtpResponse
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyProfileRepo:BaseRepository() {
    fun executeApplyLeaveApi(req:ApplyLeaveReq,responseLiveData: MutableLiveData<ApiResponse<SuccessMsgResponse>>){
        val call = apiService.applyLeave(req)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<SuccessMsgResponse> {
            override fun onResponse(
                call: Call<SuccessMsgResponse>,
                response: Response<SuccessMsgResponse>
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

            override fun onFailure(call: Call<SuccessMsgResponse>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("LoginRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }
    fun executeMarkPresentApi(req: MarkPresentRepo, responseLiveData: MutableLiveData<ApiResponse<SuccessMsgResponse>>){
        val call = apiService.addEmployeeAttendance(req)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<SuccessMsgResponse> {
            override fun onResponse(
                call: Call<SuccessMsgResponse>,
                response: Response<SuccessMsgResponse>
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

            override fun onFailure(call: Call<SuccessMsgResponse>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("LoginRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }

    fun executeProfileApi(
        userId: Int,
        responseLiveData: MutableLiveData<ApiResponse<BaseResponse<UserResponse>>>
    ) {
        val call = apiService.employeeProfile(userId)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<BaseResponse<UserResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<UserResponse>>,
                response: Response<BaseResponse<UserResponse>>
            ) {
                try {
                    if (response.code() == Constants.NetworkConstant.API_SUCCESS) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        val jObjError = JSONObject(
                            response.errorBody()!!.string()
                        )
                        val jObjErrorMessage = jObjError.getString("message")
                        val jObjErrorCode = jObjError.getString("code")
                        mLog(jObjErrorMessage)
                        mLog(jObjErrorCode)
                        responseLiveData.postValue(ApiResponse.error(Throwable(jObjErrorMessage)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(call: Call<BaseResponse<UserResponse>>, t: Throwable) {
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