package com.emines_employee.screen.dashboard

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseResponse
import com.emines_employee.base.BaseViewModel
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse

class MainViewModel(private val repo: MainRepo) : BaseViewModel() {
    val profileResponse = MutableLiveData<ApiResponse<BaseResponse<UserResponse>>>()
    fun hitProfileApi(userId: Int) {
        repo.executeProfileApi(userId, profileResponse)
    }

}