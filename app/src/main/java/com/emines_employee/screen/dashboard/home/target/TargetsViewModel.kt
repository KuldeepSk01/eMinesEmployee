package com.emines_employee.screen.dashboard.home.target

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.model.response.target.MyTargetsResponse
import com.emines_employee.model.response.target.TargetDetailResponse
import com.emines_employee.network.ApiResponse

class TargetsViewModel(private val repo:TargetsRepo) : BaseViewModel() {
    private val targetsResponse = MutableLiveData<ApiResponse<MyTargetsResponse>>()
    private val targetsDetailResponse = MutableLiveData<ApiResponse<CollectionBaseResponse<TargetDetailResponse>>>()
    fun hitEmpTargetApi(userId: Int,year:String) {
        repo.executeTargetsApi(userId,year, targetsResponse)
    }
    fun getTargetResponse():MutableLiveData<ApiResponse<MyTargetsResponse>> = targetsResponse



    fun hitEmpTargetDetailApi(userId: Int,year:String,quarterName:String) {
        repo.executeTargetDetailApi(userId,year,quarterName,targetsDetailResponse)
    }
    fun getTargetDetailResponse():MutableLiveData<ApiResponse<CollectionBaseResponse<TargetDetailResponse>>> = targetsDetailResponse

}