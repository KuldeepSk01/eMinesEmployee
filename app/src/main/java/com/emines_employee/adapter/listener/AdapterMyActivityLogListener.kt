package com.emines_employee.adapter.listener

import com.emines_employee.model.response.ActivityLogResponse

interface AdapterMyActivityLogListener {
    fun onClickActivityLog(model: ActivityLogResponse)
}