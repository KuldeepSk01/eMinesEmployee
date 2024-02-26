package com.emines_employee.screen.otpverification

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.BaseResponse
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpVerifyRepo:BaseRepository() {
    fun executeVerifyOtpApi(mobile:String,otp:String,responseLiveData: MutableLiveData<ApiResponse<BaseResponse<UserResponse>>>){
        val call = apiService.verifyOtp(mobile,otp)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<BaseResponse<UserResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<UserResponse>>,
                response: Response<BaseResponse<UserResponse>>
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