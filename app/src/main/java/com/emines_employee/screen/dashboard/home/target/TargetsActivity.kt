package com.emines_employee.screen.dashboard.home.target

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.AdapterDateRange
import com.emines_employee.adapter.TargetsAdapter
import com.emines_employee.adapter.listener.OnTargetClickListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityTargetBinding
import com.emines_employee.databinding.DialogPopupListBinding
import com.emines_employee.model.response.target.MyTargetsResponse
import com.emines_employee.model.response.target.Quarters
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.isConnectionAvailable
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import org.koin.core.component.inject
import java.util.Calendar

class TargetsActivity : BaseActivity(), OnTargetClickListener {

    private lateinit var attendanceTargetBinding: ActivityTargetBinding
    private val mViewModel: TargetsViewModel by inject()
    private var targetList = mutableListOf<Quarters>()
    private var yearRangeList = getYearList()
    private var dateRange = ""

    override val layoutId: Int
        get() = R.layout.activity_target

    override fun onCreateInit(binding: ViewDataBinding?) {
        attendanceTargetBinding = binding as ActivityTargetBinding

        if (isConnectionAvailable()) {
            mViewModel.hitEmpTargetApi(mPref.getUserDetail()?.id!!, "2024-2025")
            mViewModel.getTargetResponse().observe(this@TargetsActivity, targetDataObserver)
        } else {
            mToast(getString(R.string.oops_no_internet_available))
        }


        mLog("date ${yearRangeList}")


        attendanceTargetBinding.apply {
            toolbarTargets.apply {
                tvToolBarTitle.text = getString(R.string.target)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

                ivToolBarRightIcon.visibility = View.VISIBLE
                ivToolBarRightIcon.setOnClickListener {
                    popupDialog(this@TargetsActivity, yearRangeList)
                }
            }

        }
    }


    private val targetDataObserver: Observer<ApiResponse<MyTargetsResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    targetList.clear()
                    val data = it.data?.data

                    val t1 = (data?.quarter_one as Quarters)
                    t1.quarterName = "quarter_one"
                    val t2 = (data?.quarter_two as Quarters)
                    t2.quarterName = "quarter_two"
                    val t3 = (data?.quarter_three as Quarters)
                    t3.quarterName = "quarter_three"
                    val t4 = (data?.quarter_four as Quarters)
                    t4.quarterName = "quarter_four"
                    //  setTargetList(it.data?.data)
                    /* targetList.add(data?.quarter_one!!)
                     targetList.add(data.quarter_two!!)
                     targetList.add(data.quarter_three!!)
                     targetList.add(data.quarter_four!!)*/

                    targetList.add(t1)
                    targetList.add(t2)
                    targetList.add(t3)
                    targetList.add(t4)
                    setTargetList(targetList)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    targetList.clear()
                    setTargetList(targetList)
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun setTargetList(targetList: MutableList<Quarters>) {
        attendanceTargetBinding.rvATargets.apply {
            layoutManager =
                LinearLayoutManager(this@TargetsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = TargetsAdapter(targetList, this@TargetsActivity, this@TargetsActivity)
        }
    }

    /*  private fun setTargetList(targetList) {
          val list = mutableListOf<Quarters>()
          list.add(data?.quarter_one!!)
          list.add(data.quarter_two!!)
          list.add(data.quarter_three!!)
          list.add(data.quarter_four!!)
          targetList.addAll(list)
          attendanceTargetBinding.rvATargets.apply {
              layoutManager =
                  LinearLayoutManager(this@TargetsActivity, LinearLayoutManager.VERTICAL, false)
              adapter = TargetsAdapter(targetList, this@TargetsActivity, this@TargetsActivity)
          }
      }*/

    override fun onTargetCLick(target: Quarters) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, target)
        launchActivity(
            TargetDetailActivity::class.java,
            Constants.DefaultConstant.BUNDLE_KEY, b
        )
    }


    fun getYearList(): MutableList<String> {
        val list = arrayListOf<String>()
        var s = ""
        for (i in 2024..Calendar.getInstance().get(Calendar.YEAR)) {
            s = "$i-${i.plus(1)} "
            list.add(s)
        }
        return list
    }


    fun popupDialog(context: Context, list: MutableList<String>) {
        val alertDialog = Dialog(context)
        val b = DataBindingUtil.inflate<DialogPopupListBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_popup_list,
            null,
            false
        )
        b.tvAllBuyerSeller.text = getString(R.string.select_date)
        b.rvSelectTime.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            adapter = AdapterDateRange(list,
                context,
                object : AdapterDateRange.OnDateRangePickListener {
                    override fun onTimePicker(str: String) {
                        Log.d("TAG", "onTimePicker: date range $str")
                        // dateRange = str
                        if (isConnectionAvailable()) {
                            mViewModel.hitEmpTargetApi(mPref.getUserDetail()?.id!!, str)
                            mViewModel.getTargetResponse()
                                .observe(this@TargetsActivity, targetDataObserver)
                        } else {
                            mToast(getString(R.string.oops_no_internet_available))
                        }

                        alertDialog.dismiss()
                    }
                })
        }
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(b.root)
        alertDialog.create()
        alertDialog.show()
    }

}