package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.databinding.ItemEmployeeAttendanceBinding
import com.emines_employee.model.response.Attendance
import com.emines_employee.util.mLog

class AttendanceAdapter(
    val list: MutableList<Attendance>, val context: Context
) :
    RecyclerView.Adapter<AttendanceAdapter.AttendanceVM>() {

    class AttendanceVM(val b: ItemEmployeeAttendanceBinding) : ViewHolder(b.root)
    /*
        class AttendanceYearlyVM(val b: SubItemEmployeeAttendanceBinding) : ViewHolder(b.root)
    */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceVM {
        return AttendanceVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_employee_attendance,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AttendanceVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            model.let {
                if (it.currentLocation.isNullOrEmpty()) {
                    cl3LocationItemEA.visibility = View.GONE
                } else {
                    cl3LocationItemEA.visibility = View.VISIBLE
                }
                tvCheckInTimeItemEA.text = it.attendanceTime
                tvCheckInLocationAddressItemEA.text = it.currentLocation
                mLog(it.toString())

                tvCheckInDateItemEA.text = it.dateValue
                tvPresentStatusItemEA.apply {
                    if (it.isPresent == "0") {
                        this.text = context.getString(R.string.absent)
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.red_alert_color,
                                null
                            )
                        )
                    } else if (it.isPresent == "1") {
                        this.text = context.getString(R.string.present)
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.status_bar_color_1,
                                null
                            )
                        )
                    } else {
                        this.text = context.getString(R.string.not_mark)
                        this.setTextColor(
                            ResourcesCompat.getColor(
                                context.resources,
                                R.color.red_alert_color,
                                null
                            )
                        )
                    }

                }
            }

        }

    }

    override fun getItemCount() = list.size


}