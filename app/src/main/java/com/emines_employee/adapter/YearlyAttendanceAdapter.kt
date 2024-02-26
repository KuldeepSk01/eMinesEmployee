package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.adapter.listener.AdepterAttendanceListener
import com.emines_employee.databinding.ItemEmployeeYearlyAttendanceBinding
import com.emines_employee.model.response.Attendance

class YearlyAttendanceAdapter(
    val list: MutableList<Attendance>, val context: Context, val listener: AdepterAttendanceListener
) :
    RecyclerView.Adapter<YearlyAttendanceAdapter.YearlyAttendanceVM>() {

    class YearlyAttendanceVM(val b: ItemEmployeeYearlyAttendanceBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearlyAttendanceVM {
        return YearlyAttendanceVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_employee_yearly_attendance,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: YearlyAttendanceVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            Glide.with(context).load("https://cdn-icons-png.flaticon.com/512/6195/6195762.png")
                .into(tvCheckInSubItemEA)
            tvCheckInTimeSubItemEA.text = model.year
            tvPresentCountItemEA.text = String.format(
                "%s %s",
                model.present.toString(),
                context.getString(R.string.day_txet)
            )
            tvAbsentCountItemEA.text = String.format(
                "%s %s",
                model.absent.toString(),
                context.getString(R.string.day_txet)
            )
            tvLeaveCountItemEA.text =
                String.format("%s %s", model.leave.toString(), context.getString(R.string.day_txet))

            clYearAttendance.setOnClickListener {
                listener.onClickYearAttendance(model)
            }
        }

    }
}