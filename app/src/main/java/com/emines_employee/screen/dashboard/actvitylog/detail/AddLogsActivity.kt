package com.emines_employee.screen.dashboard.actvitylog.detail

import android.app.Dialog
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.emines_employee.CustomDialogs
import com.emines_employee.CustomDialogs.popupDialog
import com.emines_employee.CustomDialogs.showDatePickerDialog
import com.emines_employee.CustomDialogs.showTimePickerDialog
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.ActivityAddLogsBinding
import com.emines_employee.model.request.ActivityLogRequest
import com.emines_employee.model.response.AddActivityLogResponse
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.actvitylog.LOGActivityViewModel
import com.emines_employee.screen.dashboard.buyer.BuyersViewModel
import com.emines_employee.screen.dashboard.seller.SellerViewModel
import com.emines_employee.util.OnDropDownListener
import com.emines_employee.util.dropDownPopup
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import org.koin.core.component.inject

class AddLogsActivity : BaseActivity() {
    private lateinit var b: ActivityAddLogsBinding
    private val buyerViewModel: BuyersViewModel by inject()
    private val sellerViewModel: SellerViewModel by inject()

    private var buyerSellerList = mutableListOf<BuyersResponse>()
    private val mViewModel: LOGActivityViewModel by inject()
    private  var partyID:Int?=null


    override val layoutId: Int
        get() = R.layout.activity_add_logs


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateInit(binding: ViewDataBinding?) {
        b = binding as ActivityAddLogsBinding
        b.apply {
            toolbarBCA.apply {
                tvToolBarTitle.text = getString(R.string.add_activity_log)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }



            etTypeLogs.setOnClickListener {
                dropDownPopup(
                    this@AddLogsActivity,
                    it,
                    R.menu.menu_activity_log_types,
                    object : OnDropDownListener {
                        override fun onDropDownClick(item: String) {
                            etTypeLogs.text = item
                        }
                    }).show()
            }

            etPartyLogs.setOnClickListener {
                dropDownPopup(
                    this@AddLogsActivity,
                    it,
                    R.menu.menu_activity_log_party_types,
                    object : OnDropDownListener {
                        override fun onDropDownClick(item: String) {

                            if (item == getString(R.string.buyer) || item == getString(R.string.seller)) {
                                llPartyName.visibility = View.VISIBLE
                                llNewPartyName.visibility = View.GONE
                                etNewPartyName.setText("")
                            } else {
                                etPartyName.setText("")
                                llPartyName.visibility = View.GONE
                                llNewPartyName.visibility = View.VISIBLE
                            }
                            etPartyLogs.text = item
                        }
                    }).show()

            }



            etPartyName.setOnClickListener {
                val activityLogPartyType = etPartyLogs.text.toString()
                if (activityLogPartyType.isNullOrEmpty()) {
                    mToast("Party type can't be blank!")
                    return@setOnClickListener
                }
                if (activityLogPartyType == getString(R.string.buyer)) {
                    // etPartyName.setText(getString(R.string.buyer))
                    buyerViewModel.hitBuyerListApi()
                    buyerViewModel.getBuyersListResponse()
                        .observe(this@AddLogsActivity, buyerListResponseObserver)
                } else {
                    //etPartyName.setText(getString(R.string.seller))
                    sellerViewModel.hitSellerListApi()
                    sellerViewModel.getSellerListResponse()
                        .observe(this@AddLogsActivity, sellerListResponseObserver)
                }
            }

            etDateLogs.setOnClickListener {
                showDatePickerDialog(this@AddLogsActivity,
                    object : CustomDialogs.DatePickerDialogListener {
                        override fun onPicker(d: Dialog, str: String) {
                            etDateLogs.text = str
                            d.dismiss()
                        }
                    }).show()

            }

            etTimeLogs.setOnClickListener {
                showTimePickerDialog(this@AddLogsActivity,
                    object : CustomDialogs.DatePickerDialogListener {
                        override fun onPicker(d: Dialog, str: String) {
                            etTimeLogs.text = str
                            d.dismiss()
                        }
                    }).show()
            }
            etStatusLogs.setOnClickListener {
                dropDownPopup(
                    this@AddLogsActivity,
                    it,
                    R.menu.menu_activity_log_status_types,
                    object : OnDropDownListener {
                        override fun onDropDownClick(item: String) {
                            etStatusLogs.text = item
                        }
                    }).show()
            }

            tvAddLogsBtn.setOnClickListener {
                val activityLogType = etTypeLogs.text.toString()
                val activityLogPartyType = etPartyLogs.text.toString()
                val activityLogPartyName = etPartyName.text.toString()
                val activityLogNEwPartyName = etNewPartyName.text.toString()
                val activityLogStatusType = etStatusLogs.text.toString()
                val activityLogDate = etDateLogs.text.toString()
                val activityLogTime = etTimeLogs.text.toString()
                val activityLogRemark = etRemarkLogs.text.toString()


                if (activityLogType.isNullOrEmpty()) {
                    mToast("Activity type can't be blank!")
                    return@setOnClickListener
                }

                if (activityLogPartyType.isNullOrEmpty()) {
                    mToast("Party type can't be blank!")
                    return@setOnClickListener
                }

                if (activityLogNEwPartyName.isNullOrEmpty() && activityLogPartyName.isNullOrEmpty()) {
                    mToast("Party Name can't be blank!")
                    return@setOnClickListener
                }

                if (activityLogDate.isNullOrEmpty()) {
                    mToast("Date can't be blank!")
                    return@setOnClickListener
                }

                if (activityLogTime.isNullOrEmpty()) {
                    mToast("Time can't be blank!")
                    return@setOnClickListener
                }
                if (activityLogStatusType.isNullOrEmpty()) {
                    mToast("Status can't be blank!")
                    return@setOnClickListener
                }

                val req = ActivityLogRequest().apply {
                    employeeId = mPref.getUserDetail()?.id
                    partyId = if (activityLogPartyType==getString(R.string.other)) null else partyID
                    activityType = activityLogType
                    partyType = activityLogPartyType
                    partyName = if (activityLogNEwPartyName.isNullOrEmpty()) activityLogPartyName else activityLogNEwPartyName
                    date = activityLogDate
                    time = activityLogTime
                    status = activityLogStatusType
                    remark = activityLogRemark
                }

                mViewModel.hitAddActivityLogApi(req)
                mViewModel.getAddActivityLogResponse().observe(this@AddLogsActivity,addActivityLogDataObserver)

                mLog("Added Successfully data ${req.toString()}")
            }

        }
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
                    finish()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message!!)
                }
            }

        }
    }


    private val buyerListResponseObserver: Observer<ApiResponse<CollectionBaseResponse<BuyersResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    buyerSellerList.clear()
                    buyerSellerList = it.data?.data as MutableList<BuyersResponse>
                    mLog("Buyers  ${buyerSellerList.toString()}")
                    setSellerList(buyerSellerList, "All Buyers")

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private val sellerListResponseObserver: Observer<ApiResponse<CollectionBaseResponse<BuyersResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    buyerSellerList.clear()
                    buyerSellerList = it.data?.data as MutableList<BuyersResponse>
                    mLog("Sellers  ${buyerSellerList.toString()}")

                    setSellerList(buyerSellerList, "All Sellers")

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun setSellerList(buyerSellerList: MutableList<BuyersResponse>, title: String) {
        popupDialog(
            this@AddLogsActivity,
            buyerSellerList,
            object : CustomDialogs.OnPopupItemPickerListener {
                override fun onItemPick(model: BuyersResponse) {
                    b.etPartyName.text = model.name
                    partyID = model.id
                }

            },
            title
        )

    }


}