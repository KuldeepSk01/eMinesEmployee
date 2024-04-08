package com.emines_employee.screen.dashboard.actvitylog

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.model.request.ActivityLogRequest
import com.emines_employee.model.response.ActivityLogResponse
import com.emines_employee.model.response.AddActivityLogResponse
import com.emines_employee.network.ApiResponse

class LOGActivityViewModel(val repo: LOGActivityRepo) : BaseViewModel() {

    private val activityLogsListResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<ActivityLogResponse>>>()

    private val addActivityLogResponse =
        MutableLiveData<ApiResponse<AddActivityLogResponse>>()

    fun hitActivityLogListApi(req: ActivityLogRequest) {
        repo.executeActivityLogListApi(req,activityLogsListResponse)
    }

    fun getActivityLogListResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<ActivityLogResponse>>> {
        return activityLogsListResponse
    }



    fun hitAddActivityLogApi(req: ActivityLogRequest) {
        repo.executeAddActivityLogApi(req,addActivityLogResponse)
    }

    fun getAddActivityLogResponse(): MutableLiveData<ApiResponse<AddActivityLogResponse>> {
        return addActivityLogResponse
    }

}