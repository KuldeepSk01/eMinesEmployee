package com.emines_employee.screen.login

import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityLoginBinding
import com.emines_employee.model.response.LoginOtpResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.otpverification.OTPVerificationActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.isConnectionAvailable
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.validation.ValidationResult
import com.emines_employee.util.validation.ValidationState
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    override val layoutId = R.layout.activity_login
    private val mViewModel: LoginViewModel by inject()

    override fun onCreateInit(binding: ViewDataBinding?) {
        loginBinding = binding as ActivityLoginBinding
        loginBinding.apply {

            tvSendOtpBtn.setOnClickListener {
                mViewModel.isValidData(etPhoneLogin.text.toString())
                mViewModel.validationResponseObserver.observe(
                    this@LoginActivity,
                    validationObserver
                )

            }

        }

    }


    private val validationObserver: Observer<ValidationState> by lazy {
        Observer {
            when (it.msg) {
                ValidationResult.EMPTY_MOBILE_NUMBER, ValidationResult.VALID_MOBILE_NUMBER -> {
                    loginBinding.etPhoneLogin.error = getString(it.code)
                    loginBinding.etPhoneLogin.requestFocus()
                }

                ValidationResult.SUCCESS -> {
                    if (!isConnectionAvailable()) {
                        mToast("Internet not available")
                        return@Observer
                    } else {
                        mViewModel.hitLoginApi(loginBinding.etPhoneLogin.text.toString())
                        mViewModel.loginOTPResponse.observe(this@LoginActivity,loginDataObserver)
                    }
                }

                else -> {}
            }
        }
    }

    private val loginDataObserver:Observer<ApiResponse<LoginOtpResponse>> by lazy{
        Observer{
            when(it.status){
                ApiResponse.Status.LOADING->{
                    showProgress()

                }
                ApiResponse.Status.SUCCESS->{
                    hideProgress()
                    val b = Bundle()
                    b.putSerializable(Constants.DefaultConstant.OTP_DETAIL,it.data!!)
                    launchActivity(OTPVerificationActivity::class.java,Constants.DefaultConstant.BUNDLE_KEY,b)

                }
                ApiResponse.Status.ERROR->{
                    hideProgress()
                    mToast(it.error?.message!!)
                }
            }
        }
    }




}