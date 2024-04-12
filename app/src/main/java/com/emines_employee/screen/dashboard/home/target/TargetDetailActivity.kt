package com.emines_employee.screen.dashboard.home.target

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.TargetsDetailAdapter
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.ActivityTargetDetailBinding
import com.emines_employee.model.response.target.Quarters
import com.emines_employee.model.response.target.TargetDetailResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.isConnectionAvailable
import com.emines_employee.util.mToast
import com.emines_employee.util.serializable
import org.koin.core.component.inject

class TargetDetailActivity : BaseActivity() {
    private lateinit var targetDetailBinding: ActivityTargetDetailBinding
    private val mViewModel: TargetsViewModel by inject()


    override val layoutId: Int
        get() = R.layout.activity_target_detail

    override fun onCreateInit(binding: ViewDataBinding?) {
        targetDetailBinding = binding as ActivityTargetDetailBinding
        val m = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<Quarters>(Constants.DefaultConstant.MODEL_DETAIL)!!

        if (isConnectionAvailable()) {
            mViewModel.hitEmpTargetDetailApi(mPref.getUserDetail()?.id!!, m.year,m.quarterName)
            mViewModel.getTargetDetailResponse().observe(this@TargetDetailActivity, targetDetailDataObserver)
        } else {
            mToast(getString(R.string.oops_no_internet_available))
        }


        targetDetailBinding.apply {
            toolbarTargetDetail.apply {
                tvToolBarTitle.text = getString(R.string.target_detail)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }


        }

    }


    private val targetDetailDataObserver: Observer<ApiResponse<CollectionBaseResponse<TargetDetailResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    val data = it.data?.data as MutableList
                    setTargetList(data)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun setTargetList(targetList: MutableList<TargetDetailResponse>) {
        targetDetailBinding.rvATargetDetail.apply {
            layoutManager =
                LinearLayoutManager(this@TargetDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = TargetsDetailAdapter(targetList, this@TargetDetailActivity)
        }
    }

}