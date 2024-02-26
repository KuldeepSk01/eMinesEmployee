package com.emines_employee.screen.home2

import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.Home2Binding

class Home2Activity : BaseActivity() {

    private lateinit var homeBinding: Home2Binding
    override val layoutId: Int
        get() = R.layout.home2

    override fun onCreateInit(binding: ViewDataBinding?) {
        homeBinding = binding as Home2Binding

    }

}