package com.emines_employee.screen.verifyme2

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.CustomDialogs
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.R
import com.emines_employee.adapter.MultipleDateAdapter
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.databinding.ActivityApplyLeaveBinding
import com.emines_employee.model.request.ApplyLeaveReq
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.otpverification.OTPVerificationActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.OnDropDownListener
import com.emines_employee.util.dropDownPopup
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.google.android.gms.common.api.Api
import org.koin.core.component.inject

class ApplyLeaveActivity : BaseActivity() {
    private lateinit var mBinding: ActivityApplyLeaveBinding
    private val mViewModel: ApplyLeaveViewModel by inject()
    private val userDetail = mPref.getUserDetail()

    private var typeOfLeave:String?=null
    private var typeOfDay:String?=null
    private var leaveDateSetList = mutableSetOf<String>()
    private var leaveDateList = mutableListOf<String>()
    private var isMultiDayLeave:Int = 0


    override val layoutId: Int
        get() = R.layout.activity_apply_leave

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityApplyLeaveBinding
        mViewModel.mActivity = this@ApplyLeaveActivity
        mBinding.mViewModel = mViewModel
        mLog(userDetail.toString())

        mBinding.apply {
            isSelected(tvFullDayBtnAL)
            toolbarVerifyMe2.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.apply_leave)
            }

            tvTypeOfLeaveBtnAL.setOnClickListener {
                tvTypeOfLeaveBtnAL.background = ResourcesCompat.getDrawable(resources,R.drawable.seelcted_btn_drawable,null)

                dropDownPopup(this@ApplyLeaveActivity,it,R.menu.menu_types_of_leave,object:OnDropDownListener{
                    override fun onDropDownClick(item: String) {
                        tvTypeOfLeaveBtnAL.setText(item)
                        typeOfLeave  = item
                    }
                }).show()
            }
            tvFullDayBtnAL.setOnClickListener {
                isSelected(it)

            }
            tvHalfDayBtnAL.setOnClickListener {
                isSelected(it)
            }

            tvMultiDayBtnAL.setOnClickListener {
                isMultiDayLeave = 1
              //  tvMultiDayBtnAL.background = ResourcesCompat.getDrawable(resources,R.drawable.seelcted_btn_drawable,null)
               CustomDialogs.showDatePickerDialog(this@ApplyLeaveActivity,object :CustomDialogs.DatePickerDialogListener{
                    override fun onPicker(d: Dialog, str: String) {
                        leaveDateList.clear()
                        leaveDateSetList.add(str)
                        leaveDateList.addAll(leaveDateSetList)
                        setDateList(leaveDateList)
                        d.dismiss()
                    }
                }).show()

            }

            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.cancel_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.apply {
                    text = getString(R.string.apply_cap)
                    setOnClickListener {
                        val req = ApplyLeaveReq().apply {
                            userId = userDetail?.id!!
                            leaveType = typeOfLeave
                            dayType = typeOfDay
                            leaveDates = leaveDateList
                            isMultiDay = isMultiDayLeave
                        }
                        mViewModel?.hitApplyLeaveApi(req)
                        mViewModel?.applyLeaveResponse?.observe(this@ApplyLeaveActivity,applyLeaveObserver)

                    }
                }
            }




        }

    }

    private fun setDateList(leaveDateList: MutableList<String>) {
        mBinding.apply {
            rvMultiDate.layoutManager = LinearLayoutManager(this@ApplyLeaveActivity)
            rvMultiDate.adapter = MultipleDateAdapter(leaveDateList)
        }

    }

    private val applyLeaveObserver:Observer<ApiResponse<SuccessMsgResponse>>by lazy {
        Observer {
            when(it.status){
                ApiResponse.Status.LOADING->{showProgress()}
                ApiResponse.Status.SUCCESS->{
                    hideProgress()
                    CustomDialogs.showSuccessDialog(this@ApplyLeaveActivity,getString(R.string.done),"You have successfully applied the leaves","",
                        object : CustomDialogs.CustomDialogsListener {
                            override fun onComplete(d: Dialog) {
                                launchActivity(MainActivity::class.java)
                                d.dismiss()
                            }
                        }).show()
                }
                ApiResponse.Status.ERROR->{
                    hideProgress()
                    mToast(it.error?.message!!)
                }
            }
        }
    }

    private fun isSelected(it: View?) {
        mBinding.apply {
            tvFullDayBtnAL.background = ResourcesCompat.getDrawable(resources,R.drawable.default_btn_drawable3,null)
            tvHalfDayBtnAL.background = ResourcesCompat.getDrawable(resources,R.drawable.default_btn_drawable3,null)
            (  it as AppCompatTextView).background = ResourcesCompat.getDrawable(resources,R.drawable.seelcted_day_btn_drawable,null)
            typeOfDay = it.text.toString()
        }

    }
}