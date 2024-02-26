package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.AdepterAttendanceListener
import com.emines_employee.databinding.SubItemEmployeeAttendanceBinding
import com.emines_employee.model.response.Attendance

class SubAttendanceAdapter(
    val list: MutableList<Attendance>, val context: Context, val listener: AdepterAttendanceListener
) :
    RecyclerView.Adapter<SubAttendanceAdapter.SubAttendanceVM>() {

    class SubAttendanceVM(val b: SubItemEmployeeAttendanceBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubAttendanceVM {
        return SubAttendanceVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.sub_item_employee_attendance,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: SubAttendanceVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvCheckInTimeSubItemEA.text = model.month
            tvPresentCountItemEA.text = model.present.toString()
            tvAbsentCountItemEA.text = model.absent.toString()
            tvLeaveCountItemEA.text = model.leave.toString()
            clYearAttendance.setOnClickListener {
                listener.onClickMonthAttendance(model)
            }
        }

    }
}