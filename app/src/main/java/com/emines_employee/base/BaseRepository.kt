package com.emines_employee.base

import com.emines_employee.network.ApiService
import com.emines_employee.preference.reflection.ReflectionUtil
import com.google.gson.Gson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseRepository : KoinComponent {
    val apiService: ApiService by inject()
    val reflectionUtil: ReflectionUtil by inject()

}