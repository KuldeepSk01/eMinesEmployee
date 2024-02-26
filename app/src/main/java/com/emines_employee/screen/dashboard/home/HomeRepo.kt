package com.emines_employee.screen.dashboard.home

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.BaseResponse1
import com.emines_employee.model.response.DashboardResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepo : BaseRepository() {
    fun executeDashboardApi(
        userID: Int,
        responseLiveData: MutableLiveData<ApiResponse<BaseResponse1<DashboardResponse>>>
    ) {
        val call = apiService.dashboardData(userID)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<BaseResponse1<DashboardResponse>> {
            override fun onResponse(
                call: Call<BaseResponse1<DashboardResponse>>,
                response: Response<BaseResponse1<DashboardResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()!!.message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(
                call: Call<BaseResponse1<DashboardResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("Home Repo onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }

}