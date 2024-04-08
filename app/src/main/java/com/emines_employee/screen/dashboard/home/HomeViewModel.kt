package com.emines_employee.screen.dashboard.home

import androidx.lifecycle.MutableLiveData
import com.emines_employee.R
import com.emines_employee.base.BaseResponse1
import com.emines_employee.base.BaseViewModel
import com.emines_employee.model.response.DashboardResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.home.activitylog.MyActivityLogActivity
import com.emines_employee.screen.dashboard.home.attendance.AttendanceSelectDateActivity
import com.emines_employee.screen.dashboard.home.target.TargetsActivity
import com.emines_employee.screen.home2.duty.DutyActivity

class HomeViewModel(private val repo: HomeRepo) : BaseViewModel() {
    var mFragment: HomeFragment? = null
    private val dashboardResponse = MutableLiveData<ApiResponse<BaseResponse1<DashboardResponse>>>()

    fun hitDashboardApi(userId: Int) {
        repo.executeDashboardApi(userId, dashboardResponse)
    }

    fun getHomeDashboardApiResponse(): MutableLiveData<ApiResponse<BaseResponse1<DashboardResponse>>> {
        return dashboardResponse
    }


    fun onClickAttendance() {
        mFragment?.launchActivity(AttendanceSelectDateActivity::class.java)
    }

    fun onClickActivityLog() {
        mFragment?.launchActivity(MyActivityLogActivity::class.java)
    }
    fun onClickTargets() {
        mFragment?.launchActivity(TargetsActivity::class.java)
    }

    fun onClickAddBuyer() {
        // mFragment?.launchActivity(BuyerContactPersonActivity::class.java)
    }

    fun onClickAddSeller() {
        //  mFragment?.launchActivity(BuyerContactPersonActivity::class.java)
    }

    fun onClickCreateOrder() {
        mFragment?.launchActivity(DutyActivity::class.java)
    }

    fun onClickCreatePickup() {
        // mFragment?.launchActivity(BuyerContactPersonActivity::class.java)
    }

    fun onClickMyOrder() {
        // mFragment?.launchActivity(BuyerContactPersonActivity::class.java)
    }

    fun onClickMyPickup() {
        //mFragment?.launchActivity(BuyerContactPersonActivity::class.java)
    }

    private fun getSliderList(): MutableList<String> {
        val list = mutableListOf<String>()
        list.add(mFragment!!.getString(R.string.explore_info))
        list.add(mFragment!!.getString(R.string.explore_info))
        list.add(mFragment!!.getString(R.string.explore_info))
        list.add(mFragment!!.getString(R.string.explore_info))
        return list
    }
}