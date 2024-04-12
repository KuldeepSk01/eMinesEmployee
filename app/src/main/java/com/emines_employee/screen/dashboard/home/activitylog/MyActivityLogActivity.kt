package com.emines_employee.screen.dashboard.home.activitylog

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityMyLogBinding
import com.emines_employee.model.MyActivityLogsModel
import com.emines_employee.screen.dashboard.actvitylog.detail.AddLogsActivity

class MyActivityLogActivity : BaseActivity() {
    private lateinit var b: ActivityMyLogBinding
    override val layoutId: Int
        get() = R.layout.activity_my_log

    override fun onCreateInit(binding: ViewDataBinding?) {
        b = binding as ActivityMyLogBinding
        b.apply {
            toolbarActivityLog.apply {
                //tvToolBarTitle.setTextColor(getColor(R.color.white))
                tvToolBarTitle.text = getString(R.string.activity_log)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }

            rvActivityLog.apply {
                layoutManager = LinearLayoutManager(
                    this@MyActivityLogActivity, LinearLayoutManager.VERTICAL, false
                )
              //  adapter = MyActivityLogAdapter(getActivityLog(), this@MyActivityLogActivity, this@MyActivityLogActivity)

            }

            tvAddActivityLogBtn.setOnClickListener {
                launchActivity(AddLogsActivity::class.java)
            }
        }
    }

    fun getActivityLog(): MutableList<MyActivityLogsModel> {
        val list = arrayListOf<MyActivityLogsModel>()
        list.add(
            MyActivityLogsModel(
                1,
                "Call",
                "Buyer",
                "Sandeep",
                "01 Apr 2024",
                "10:12 am",
                "Scheduled",
                "I have to quit the application"
            )
        )
        list.add(
            MyActivityLogsModel(
                2,
                "Meeting",
                "Buyer",
                "Sandeep",
                "01 Apr 2024",
                "10:12 am",
                "Scheduled",
                "I have to quit the application"
            )
        )
        list.add(
            MyActivityLogsModel(
                3,
                "Call",
                "Buyer",
                "Sandeep",
                "01 Apr 2024",
                "10:12 am",
                "Scheduled",
                "I have to quit the application"
            )
        )
        list.add(
            MyActivityLogsModel(
                4,
                "Call",
                "Buyer",
                "Sandeep",
                "01 Apr 2024",
                "10:12 am",
                "Scheduled",
                "I have to quit the application"
            )
        )
        list.add(
            MyActivityLogsModel(
                5,
                "Call",
                "Buyer",
                "Sandeep",
                "01 Apr 2024",
                "10:12 am",
                "Scheduled",
                "I have to quit the application"
            )
        )
        return list
    }

/*
    override fun onClickActivityLog(model: MyActivityLogsModel) {
        val dialog = Dialog(this@MyActivityLogActivity, android.R.style.Theme_Material)
        dialog.window?.decorView?.setBackgroundColor(getColor(android.R.color.transparent))
        val bind = DataBindingUtil.inflate<DialogEditActivitylogsBinding>(
            LayoutInflater.from(
                this
            ), R.layout.dialog_edit_activitylogs, null, false
        )

        bind.apply {
            etTypeLogs.text = model.activityType
            etPartyLogs.text = model.activityParty
            etPartyName.text = model.activityPartyName
            etDateLogs.text = model.activityDate
            etTimeLogs.text = model.activityTime
            etStatusLogs.text = model.activityStatus
            etRemarkLogs.setText(model.activityRemark)
            etStatusLogs.setOnClickListener {
                dropDownPopup(
                    this@MyActivityLogActivity,
                    it,
                    R.menu.menu_activity_log_status_types,
                    object :
                        OnDropDownListener {
                        override fun onDropDownClick(item: String) {
                            etStatusLogs.text = item
                        }
                    }).show()
            }

            tvSaveLogsBtn.setOnClickListener {
                mToast("Update successfully")
                dialog.dismiss()
            }
        }


        dialog.setContentView(bind.root)
        dialog.create()
        dialog.show()
    }
*/
}