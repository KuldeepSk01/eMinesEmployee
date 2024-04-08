package com.emines_employee.screen.dashboard.actvitylog

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.adapter.MyActivityLogAdapter
import com.emines_employee.adapter.listener.AdapterMyActivityLogListener
import com.emines_employee.base.BaseFragment
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.DialogEditActivitylogsBinding
import com.emines_employee.databinding.DialogFilterActivityLogBinding
import com.emines_employee.databinding.FragmentOrdersBinding
import com.emines_employee.model.request.ActivityLogRequest
import com.emines_employee.model.response.ActivityLogResponse
import com.emines_employee.model.response.AddActivityLogResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.home.activitylog.AddLogsActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.OnDropDownListener
import com.emines_employee.util.dropDownPopup
import com.emines_employee.util.mToast
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.core.component.inject

class LOGActivityFragment : BaseFragment(), AdapterMyActivityLogListener {

    private lateinit var b: FragmentOrdersBinding
    private lateinit var mContext: Context
    private val mViewModel: LOGActivityViewModel by inject()
    private var status: String? = null
    private var activityType: String? = null


    override fun getLayoutId() = R.layout.fragment_orders
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onResume() {
        super.onResume()
        hitLogApi(activityType, status)
    }

    override fun onCreateViewInit(binding: ViewDataBinding, view: View) {
        b = binding as FragmentOrdersBinding
        b.apply {
            mPref.put(Constants.PreferenceConstant.ACTIVITY_LOG_TYPE, "")
            mPref.put(Constants.PreferenceConstant.ACTIVITY_LOG_STATUS, "")


            toolbarActivityLog.apply {
                //tvToolBarTitle.setTextColor(getColor(R.color.white))
                tvToolBarTitle.text = getString(R.string.activity_log)
                ivToolBarBack.visibility = View.GONE
                ivToolBarRightIcon.visibility = View.VISIBLE
                ivToolBarRightIcon.setOnClickListener {
                    showBottomSheet()
                }

            }



            tvAddActivityLogBtn.setOnClickListener {
                launchActivity(AddLogsActivity::class.java)
            }
        }
    }

    private fun hitLogApi(aType: String?, s: String?) {
        val req = ActivityLogRequest().apply {
            employeeId = mPref.getUserDetail()?.id
            activityType = aType
            status = s
        }
        mViewModel.hitActivityLogListApi(req)
        mViewModel.getActivityLogListResponse().observe(requireActivity(), activityLogDataObserver)
    }

    private val activityLogDataObserver: Observer<ApiResponse<CollectionBaseResponse<ActivityLogResponse>>> by lazy {
        Observer {
            when (it.status) {

                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    val list = it.data?.data as MutableList<ActivityLogResponse>
                    if (list.isEmpty()) {
                        b.rlNoDataAvailable.visibility = View.VISIBLE
                        b.rvActivityLog.visibility = View.GONE
                    } else {
                        b.rlNoDataAvailable.visibility = View.GONE
                        b.rvActivityLog.visibility = View.VISIBLE
                    }
                    setLogList(list)

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message!!)
                }
            }

        }
    }

    private fun setLogList(list: MutableList<ActivityLogResponse>) {
        b.rvActivityLog.apply {
            layoutManager = LinearLayoutManager(
                mContext, LinearLayoutManager.VERTICAL, false
            )
            adapter = MyActivityLogAdapter(
                list, mContext, this@LOGActivityFragment
            )

        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(mContext, R.style.DialogStyle)
        val bottomSheet = DataBindingUtil.inflate<DialogFilterActivityLogBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_filter_activity_log,
            null,
            false
        )
        bottomSheet.apply {
            val aType = mPref.get(Constants.PreferenceConstant.ACTIVITY_LOG_TYPE, "")
            val aStatus = mPref.get(Constants.PreferenceConstant.ACTIVITY_LOG_STATUS, "")

            if (!aType.isNullOrEmpty()) {
                when (aType) {
                    getString(R.string.call) -> {
                        setOnFilterColor1(tvCallFilter, this)
                    }
                    getString(R.string.meeting) -> {
                        setOnFilterColor1(tvMeetingFilter, this)
                    }
                    getString(R.string.personal_task) -> {
                        setOnFilterColor1(tvPersonalTaskFilter, this)
                    }
                }
            }
            if (!aStatus.isNullOrEmpty()) {
                when (aStatus) {
                    getString(R.string.scheduled) -> {
                        setOnFilterColor2(tvScheduledFilter, this)
                    }
                    getString(R.string.re_scheduled) -> {
                        setOnFilterColor2(tvReScheduledFilter, this)
                    }
                    getString(R.string.cancelled) -> {
                        setOnFilterColor2(tvCancelledFilter, this)
                    }
                }
            }


            closeButton.setOnClickListener {
                activityType = null
                status=null
                mPref.put(Constants.PreferenceConstant.ACTIVITY_LOG_TYPE, "")
                mPref.put(Constants.PreferenceConstant.ACTIVITY_LOG_STATUS, "")
                hitLogApi(activityType,status)
                dialog.dismiss()
            }

            tvCallFilter.setOnClickListener {
                setOnFilterColor1(it, this@apply)
            }
            tvMeetingFilter.setOnClickListener {
                setOnFilterColor1(it, this@apply)
            }

            tvPersonalTaskFilter.setOnClickListener {
                setOnFilterColor1(it, this@apply)
            }

            tvScheduledFilter.setOnClickListener {
                setOnFilterColor2(it, this@apply)
            }
            tvReScheduledFilter.setOnClickListener {
                setOnFilterColor2(it, this@apply)
            }

            tvCancelledFilter.setOnClickListener {
                setOnFilterColor2(it, this@apply)
            }

            tvFilterBtn.setOnClickListener {
                hitLogApi(activityType, status)
                dialog.dismiss()
            }
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(bottomSheet.root)
        dialog.create()
        dialog.show()
    }

    private fun setOnFilterColor1(
        tv: View,
        b: DialogFilterActivityLogBinding
    ) {
        b.tvMeetingFilter.apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.hint_text_color, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.unselect_btn_drawable,
                    null
                )
            )
        }
        b.tvCallFilter.apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.hint_text_color, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.unselect_btn_drawable,
                    null
                )
            )
        }
        b.tvPersonalTaskFilter.apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.hint_text_color, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.unselect_btn_drawable,
                    null
                )
            )
        }

        (tv as AppCompatTextView).apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.default_btn_drawable,
                    null
                )
            )
        }

        activityType = (tv as AppCompatTextView).text.toString()
        mPref.put(Constants.PreferenceConstant.ACTIVITY_LOG_TYPE, activityType!!)

    }

    private fun setOnFilterColor2(
        tv: View,
        b: DialogFilterActivityLogBinding
    ) {
        b.tvScheduledFilter.apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.hint_text_color, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.unselect_btn_drawable,
                    null
                )
            )
        }
        b.tvReScheduledFilter.apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.hint_text_color, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.unselect_btn_drawable,
                    null
                )
            )
        }
        b.tvCancelledFilter.apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.hint_text_color, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.unselect_btn_drawable,
                    null
                )
            )
        }

        (tv as AppCompatTextView).apply {
            setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            setBackgroundDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.default_btn_drawable,
                    null
                )
            )
        }

        status = (tv as AppCompatTextView).text.toString()
        mPref.put(Constants.PreferenceConstant.ACTIVITY_LOG_STATUS, status!!)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClickActivityLog(model: ActivityLogResponse) {
        val dialog = Dialog(mContext, android.R.style.Theme_Material)
        dialog.window?.decorView?.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                android.R.color.transparent,
                null
            )
        )
        val bind = DataBindingUtil.inflate<DialogEditActivitylogsBinding>(
            LayoutInflater.from(
                mContext
            ), R.layout.dialog_edit_activitylogs, null, false
        )

        bind.apply {
           /* etTypeLogs.text = model.activityType
            etPartyLogs.text = model.partyType
            etPartyName.text = model.partyName*/
            etDateLogs.text = model.date
            etTimeLogs.text = model.time
            etStatusLogs.text = model.status
            etRemarkLogs.setText(model.remark)

            etDateLogs.setOnClickListener {
                CustomDialogs.showDatePickerDialog(mContext,
                    object : CustomDialogs.DatePickerDialogListener {
                        override fun onPicker(d: Dialog, str: String) {
                            etDateLogs.text = str
                            d.dismiss()
                        }
                    }).show()

            }

            etTimeLogs.setOnClickListener {
                CustomDialogs.showTimePickerDialog(mContext,
                    object : CustomDialogs.DatePickerDialogListener {
                        override fun onPicker(d: Dialog, str: String) {
                            etTimeLogs.text = str
                            d.dismiss()
                        }
                    }).show()
            }


            etStatusLogs.setOnClickListener {
                dropDownPopup(mContext, it, R.menu.menu_activity_log_status_types, object :
                    OnDropDownListener {
                    override fun onDropDownClick(item: String) {
                        etStatusLogs.text = item
                        if (model.status!=item){
                            llEditDate.visibility = View.VISIBLE
                        }else
                        {
                            llEditDate.visibility = View.GONE
                        }
                    }
                }).show()
            }

            tvSaveLogsBtn.setOnClickListener {
               // mToast("Update successfully")
                val mStatus = etStatusLogs.text.toString()
                val mRemark = etRemarkLogs.text.toString()
                val mDate = etDateLogs.text.toString()
                val mTime = etTimeLogs.text.toString()

                if(model.status!=mStatus){
                    val req = ActivityLogRequest().apply {
                        employeeId = mPref.getUserDetail()?.id
                        partyId = model.id
                        activityType = model.activityType
                        partyType = model.partyType
                        partyName = model.partyName
                        date = mDate
                        time =  mTime
                        status = mStatus
                        remark = mRemark
                    }

                    mViewModel.hitAddActivityLogApi(req)
                    mViewModel.getAddActivityLogResponse().observe(requireActivity(),addActivityLogDataObserver)


                }
                dialog.dismiss()
            }
        }


        dialog.setContentView(bind.root)
        dialog.create()
        dialog.show()
    }

    private val addActivityLogDataObserver: Observer<ApiResponse<AddActivityLogResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mToast(it.data!!.message)
                    hitLogApi(activityType,status)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message!!)
                }
            }

        }
    }

}




