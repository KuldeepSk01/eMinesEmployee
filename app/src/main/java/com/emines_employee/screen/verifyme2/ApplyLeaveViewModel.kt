package com.emines_employee.screen.verifyme2

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.ApplyLeaveReq
import com.emines_employee.network.ApiResponse

class ApplyLeaveViewModel(private val repo:VerifyProfileRepo) : BaseViewModel() {
    var mActivity:ApplyLeaveActivity?=null

    val applyLeaveResponse = MutableLiveData<ApiResponse<SuccessMsgResponse>>()
    fun hitApplyLeaveApi(req:ApplyLeaveReq){
        repo.executeApplyLeaveApi(req,applyLeaveResponse)
    }





}