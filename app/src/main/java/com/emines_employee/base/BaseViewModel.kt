package com.emines_employee.base

import androidx.lifecycle.ViewModel
import com.emines_employee.preference.PreferenceHelper
import com.emines_employee.util.validation.Validator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel : ViewModel(),KoinComponent {
    val validator: Validator by inject()
    val mPref: PreferenceHelper by inject()



}