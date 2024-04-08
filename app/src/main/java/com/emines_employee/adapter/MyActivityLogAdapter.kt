package com.emines_employee.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emines_employee.R
import com.emines_employee.adapter.listener.AdapterMyActivityLogListener
import com.emines_employee.databinding.ItemActivitylogLayoutBinding
import com.emines_employee.model.MyActivityLogsModel
import com.emines_employee.model.response.ActivityLogResponse

class MyActivityLogAdapter(val list:MutableList<ActivityLogResponse>,val context: Context,val listener: AdapterMyActivityLogListener):RecyclerView.Adapter<MyActivityLogAdapter.MyActivityLogVM>() {
    inner class MyActivityLogVM(val b:ItemActivitylogLayoutBinding):ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyActivityLogVM {
        return MyActivityLogVM(
            DataBindingUtil.inflate<ItemActivitylogLayoutBinding>(LayoutInflater.from(parent.context),R.layout.item_activitylog_layout,parent,false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyActivityLogVM, position: Int) {
        val m = list[position]
        holder.b.apply {
            tvItemPartyLogType.text = m.activityType
            tvItemPartyType.text = m.partyType
            tvItemPartyName.text = m.partyName
            tvItemPartyDate.text = m.date
            tvItemPartyTime.text = m.time
            tvItemPartyStatus.text = m.status
            tvItemPartyRemark.text = m.remark
            cvItemActivityLog.setOnClickListener {
                listener.onClickActivityLog(m)
            }
        }

    }
}