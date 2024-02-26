package com.emines_employee.base

import androidx.databinding.ViewDataBinding

interface BaseInterface {
    val layoutId: Int
    fun onCreateInit(binding: ViewDataBinding?)
}