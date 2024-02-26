package com.emines_employee.screen.dashboard.home.attendance

import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.AttendanceAdapter
import com.emines_employee.adapter.SubAttendanceAdapter
import com.emines_employee.adapter.YearlyAttendanceAdapter
import com.emines_employee.adapter.listener.AdepterAttendanceListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityAttendanceSelectDateBinding
import com.emines_employee.model.request.AttendanceSheetReq
import com.emines_employee.model.response.Attendance
import com.emines_employee.model.response.AttendanceResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.mToast
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.Calendar

class AttendanceSelectDateActivity : BaseActivity(), AdepterAttendanceListener {

    private lateinit var mBind: ActivityAttendanceSelectDateBinding
    private val mViewModel: AttendanceViewModel by inject()
    private var attendanceList = mutableListOf<Attendance>()
    private lateinit var attendanceListAdapter: AttendanceAdapter
    private var attendanceType = "Today"
    private var reverseOrder = true
    override val layoutId: Int
        get() = R.layout.activity_attendance_select_date

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAttendanceSelectDateBinding
        //hitAttendanceApi(mPref.getUserDetail()?.id!!, attendanceType)
        hitAttendanceApi(mPref.getUserDetail()?.id!!, attendanceType, getCurrentMonth())


        mBind.apply {
            ivBackAttendance.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            tvAttendanceTodayBtn.setOnClickListener {
                reverseOrder = true
                onSelectFilter(it as AppCompatTextView)
                /*  rlAttendance.visibility = View.GONE
                  rvAttendance.visibility = View.VISIBLE*/
                attendanceType = getString(R.string.today)
                hitAttendanceApi(mPref.getUserDetail()?.id!!, attendanceType)

            }

            tvAttendanceMonthlyBtn.setOnClickListener { view ->
                reverseOrder = false
                attendanceType = getString(R.string.monthly)
                onSelectFilter(view as AppCompatTextView)

                if (checkYearForAttendance()) {
                    hitAttendanceApi(
                        mPref.getUserDetail()?.id!!,
                        attendanceType,
                        null,
                        getCurrentYear()
                    )
                } else {
                    hitAttendanceApi(
                        mPref.getUserDetail()?.id!!,
                        attendanceType,
                        null,
                        getPreviousYear()
                    )
                }
            }


            tvAttendanceYearBtn.setOnClickListener {
                onSelectFilter(it as AppCompatTextView)
                reverseOrder = true
                attendanceType = getString(R.string.yearly)
                hitAttendanceApi(
                    mPref.getUserDetail()?.id!!,
                    attendanceType)
            }

        }
    }

    private fun hitAttendanceApi(
        mUid: Int,
        mType: String,
        mMonth: String? = null,
        mYear: String? = null
    ) {

        val request = AttendanceSheetReq().apply {
            userId = mUid
            type = mType
            year = mYear
            month = mMonth
        }
        mViewModel.hitAttendanceSheetApi(request)
        mViewModel.attendanceResponse.observe(
            this@AttendanceSelectDateActivity,
            attendanceDataObserver
        )

    }


    private val attendanceDataObserver: Observer<ApiResponse<AttendanceResponse<Attendance>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()

                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()

                    /*
                    mBind.tvAbsentAttendanceCount.text =
                         if (it.data?.absent!! > 9) it.data.absent.toString() else String.format(
                             "%s%s",
                             "0",it.data.absent.toString())
                     mBind.tvPresentAttendanceCount.text =
                         if (it.data.present!! > 9) it.data.present.toString() else String.format(
                             "%s%s",
                             "0",
                             it.data.present.toString())
                     */

                    attendanceList.clear()
                    attendanceList = it.data?.data as MutableList
                    if (attendanceList.isNotEmpty()) {
                        setAttendanceList(attendanceList)
                    }

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message!!)
                }
            }
        }
    }

    private fun setAttendanceList(list: MutableList<Attendance>) {
        mBind.rvAttendance.apply {
            layoutManager = LinearLayoutManager(
                this@AttendanceSelectDateActivity,
                LinearLayoutManager.VERTICAL,
                reverseOrder
            )
            when (attendanceType) {
                getString(R.string.today) -> {
                    adapter = AttendanceAdapter(list, this@AttendanceSelectDateActivity)
                }

                getString(R.string.monthly) -> {
                    adapter = SubAttendanceAdapter(
                        list,
                        this@AttendanceSelectDateActivity,
                        this@AttendanceSelectDateActivity
                    )
                }

                else -> {
                    adapter = YearlyAttendanceAdapter(
                        list,
                        this@AttendanceSelectDateActivity,
                        this@AttendanceSelectDateActivity
                    )
                }
            }
        }
    }


    private fun onSelectFilter(view: AppCompatTextView) {
        mBind.apply {
            tvAttendanceTodayBtn.background = null
            tvAttendanceMonthlyBtn.background = null
            tvAttendanceYearBtn.background = null
            tvAttendanceTodayBtn.setTextColor(
                ResourcesCompat.getColor(resources, R.color.default_text_color, null))
            tvAttendanceMonthlyBtn.setTextColor(ResourcesCompat.getColor(resources, R.color.default_text_color, null))
            tvAttendanceYearBtn.setTextColor(ResourcesCompat.getColor(resources, R.color.default_text_color, null))

        }

        view.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.attendance_select_filter_drawable, null
        )
        view.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white, null
            )
        )
    }

    override fun onClickMonthAttendance(model: Attendance) {
        reverseOrder = true
        onSelectFilter(mBind.tvAttendanceTodayBtn)
        attendanceType = getString(R.string.today)
        hitAttendanceApi(
            mPref.getUserDetail()?.id!!,
            attendanceType,
            getMonthCode(model.month.toString()),
            model.startYear
        )
    }

    override fun onClickYearAttendance(model: Attendance) {
        reverseOrder = false
        onSelectFilter(mBind.tvAttendanceMonthlyBtn)
        attendanceType = getString(R.string.monthly)
        hitAttendanceApi(
            mPref.getUserDetail()?.id!!,
            attendanceType,
            null,
            model.startYear
        )

    }


    private fun getMonthCode(month: String): String {
        val date = SimpleDateFormat("MMM").parse(month)
        val cal = Calendar.getInstance()
        cal.time = date
        val sdf = SimpleDateFormat("MM")
        return sdf.format(cal.time)
    }

    private fun getCurrentMonth(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("MM")
        return sdf.format(cal.time)
    }

    private fun getCurrentYear(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy")
        return sdf.format(cal.time)
    }

    private fun getPreviousYear(): String {
        val cal = Calendar.getInstance()
        val cYear = cal.get(Calendar.YEAR)
        val pYear = cYear - 1
        cal.set(Calendar.YEAR, pYear)
        val sdf = SimpleDateFormat("yyyy")
        return sdf.format(cal.time)
    }

    private fun checkYearForAttendance(): Boolean {
        val cMonth = Calendar.getInstance().get(Calendar.MONTH) // month start from 0
        return cMonth >= 3 // 0, 1,2,3
    }


}