package com.emines_employee.screen.dashboard.home.target

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.adapter.TargetsAdapter
import com.emines_employee.adapter.listener.OnTargetClickListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.BaseResponse
import com.emines_employee.databinding.ActivityTargetBinding
import com.emines_employee.model.Targets
import com.emines_employee.model.response.TargetsResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import org.koin.core.component.inject
import java.util.Calendar

class TargetsActivity : BaseActivity(), OnTargetClickListener {

    private lateinit var attendanceTargetBinding: ActivityTargetBinding
    private val mViewModel: TargetsViewModel by inject()
    private var targetsList = mutableListOf<Targets>()

    override val layoutId: Int
        get() = R.layout.activity_target

    override fun onCreateInit(binding: ViewDataBinding?) {
        attendanceTargetBinding = binding as ActivityTargetBinding

        mViewModel.hitEmpTargetApi(mPref.getUserDetail()?.id!!)
        mViewModel.getTargetResponse().observe(this@TargetsActivity, targetDataObserver)

        attendanceTargetBinding.apply {
            mPref.getUserDetail()?.let {
                Glide.with(this@TargetsActivity).load(it.profilePic).placeholder(R.drawable.profile_img).into(ivTargetsImg)
                tvTargetsUserName.text = String.format("%s %s",it.name,it.lastName)
                tvTargetUserMobile.text  = it.phone
                tvTargetUserEmail.text = it.email
            }

        }
    }


    private val targetDataObserver: Observer<ApiResponse<BaseResponse<TargetsResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    setTargetList(it.data?.data)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mLog(it.error?.message.toString())
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun setTargetList(data: TargetsResponse?) {
        val list = mutableListOf<Targets>()
        data?.let {
            list.add(
                Targets(
                    1,
                    it.achievedQuarterOneTargetParcentage,
                    it.quarterOneTarget,
                    it.achievedQuarterOneTarget,
                    Calendar.getInstance().get(Calendar.YEAR).toString(),
                    "JAN-FEB-MAR"
                )
            )
            list.add(
                Targets(
                    2,
                    it.achievedQuarterTwoTargetParcentage,
                    it.quarterTwoTarget,
                    it.achievedQuarterTwoTarget,
                    Calendar.getInstance().get(Calendar.YEAR).toString(),
                    "APR-MAY-JUN"
                )
            )
            list.add(
                Targets(
                    3,
                    it.achievedQuarterThreeTargetParcentage,
                    it.quarterThreeTarget,
                    it.achievedQuarterThreeTarget,
                    Calendar.getInstance().get(Calendar.YEAR).toString(),
                    "JULY-AUG-SEP"
                )
            )
            list.add(
                Targets(
                    4,
                    it.achievedQuarterFourTargetParcentage,
                    it.quarterFourTarget,
                    it.achievedQuarterFourTarget,
                    Calendar.getInstance().get(Calendar.YEAR).toString(),
                    "OCT-NOV-DEC"
                )
            )
        }

        attendanceTargetBinding.rvATargets.apply {
            layoutManager =
                LinearLayoutManager(this@TargetsActivity, LinearLayoutManager.VERTICAL, false)
            adapter =
                TargetsAdapter(list, this@TargetsActivity, this@TargetsActivity)
        }
    }

    override fun onTargetCLick(target: Targets) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL,target)
        launchActivity(TargetDetailActivity::class.java,Constants.DefaultConstant.BUNDLE_KEY,b)
    }
}