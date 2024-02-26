package com.emines_employee.screen.otpverification

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseResponse
import com.emines_employee.base.BaseViewModel
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse

class OTPVerifyViewModel(private val repo:OtpVerifyRepo) : BaseViewModel() {
    var otpVerificationActivity:OTPVerificationActivity?=null

    val verifyOtpResponse = MutableLiveData<ApiResponse<BaseResponse<UserResponse>>>()
    fun hitOtpVerifyApi(mobile: String,otp:String) {
        repo.executeVerifyOtpApi(mobile,otp, verifyOtpResponse)
    }

    fun getOtp():String{
        otpVerificationActivity?.oBinding?.apply {
            val otp = etOne.text.toString()+etTwo.text.toString()+etThree.text.toString()+etFour.text.toString()
            if (otp.isEmpty()){
                return "OTP can't blank !"
            }else if (otp.length==4){
                return otp
            }
            else{
                return "OTP must be 4 digit !"
            }
        }
        return "OTP must be 4 digit !"
    }




}