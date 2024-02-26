package com.emines_employee.screen.dashboard.home.target

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseResponse
import com.emines_employee.base.BaseViewModel
import com.emines_employee.model.response.TargetsResponse
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse

class TargetsViewModel(private val repo:TargetsRepo) : BaseViewModel() {
    private val targetsResponse = MutableLiveData<ApiResponse<BaseResponse<TargetsResponse>>>()
    fun hitEmpTargetApi(userId: Int) {
        repo.executeTargetsApi(userId, targetsResponse)
    }
    fun getTargetResponse():MutableLiveData<ApiResponse<BaseResponse<TargetsResponse>>> = targetsResponse

}