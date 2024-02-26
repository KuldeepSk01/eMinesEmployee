package com.emines_employee.screen.dashboard.home.target

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.BaseResponse
import com.emines_employee.model.response.TargetsResponse
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TargetsRepo : BaseRepository() {
    fun executeTargetsApi(
        userId: Int,
        responseLiveData: MutableLiveData<ApiResponse<BaseResponse<TargetsResponse>>>
    ) {
        val call = apiService.employeeTargets(userId)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<BaseResponse<TargetsResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<TargetsResponse>>,
                response: Response<BaseResponse<TargetsResponse>>
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

            override fun onFailure(call: Call<BaseResponse<TargetsResponse>>, t: Throwable) {
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