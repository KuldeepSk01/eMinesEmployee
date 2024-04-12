package com.emines_employee.screen.dashboard.home.target

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.CollectionBaseResponse

import com.emines_employee.model.response.target.MyTargetsResponse
import com.emines_employee.model.response.target.TargetDetailResponse

import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TargetsRepo : BaseRepository() {
    fun executeTargetsApi(
        userId: Int,
        year:String,
        responseLiveData: MutableLiveData<ApiResponse<MyTargetsResponse>>
    ) {
        val call = apiService.employeeTargets(userId,year)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<MyTargetsResponse> {
            override fun onResponse(
                call: Call<MyTargetsResponse>,
                response: Response<MyTargetsResponse>
            ) {
                try {
                    if (response.body()?.status!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(call: Call<MyTargetsResponse>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("TargetRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


    fun executeTargetDetailApi(
        userId: Int,
        year:String,
        quarterName:String,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<TargetDetailResponse>>>
    ) {
        val call = apiService.employeeTargetDetail(userId,year,quarterName)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<CollectionBaseResponse<TargetDetailResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<TargetDetailResponse>>,
                response: Response<CollectionBaseResponse<TargetDetailResponse>>
            ) {
                try {
                    if (response.body()?.status!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(call: Call<CollectionBaseResponse<TargetDetailResponse>>, t: Throwable) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    Log.d("TargetRepo", "onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }
}