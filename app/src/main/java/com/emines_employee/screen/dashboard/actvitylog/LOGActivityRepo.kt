package com.emines_employee.screen.dashboard.actvitylog

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.model.request.ActivityLogRequest
import com.emines_employee.model.response.ActivityLogResponse
import com.emines_employee.model.response.AddActivityLogResponse
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LOGActivityRepo : BaseRepository() {
    fun executeActivityLogListApi(
        request: ActivityLogRequest,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<ActivityLogResponse>>>
    ) {
        val call = apiService.activityLogsList(request)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<CollectionBaseResponse<ActivityLogResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<ActivityLogResponse>>,
                response: Response<CollectionBaseResponse<ActivityLogResponse>>
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

            override fun onFailure(
                call: Call<CollectionBaseResponse<ActivityLogResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("Buyers Repo onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }

    fun executeAddActivityLogApi(
        request: ActivityLogRequest,
        responseLiveData: MutableLiveData<ApiResponse<AddActivityLogResponse>>
    ) {
        val call = apiService.addActivityLogs(request)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<AddActivityLogResponse> {
            override fun onResponse(
                call: Call<AddActivityLogResponse>,
                response: Response<AddActivityLogResponse>
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

            override fun onFailure(
                call: Call<AddActivityLogResponse>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("Buyers Repo onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }
}
