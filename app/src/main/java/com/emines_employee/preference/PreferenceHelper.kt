package com.emines_employee.preference

import android.content.SharedPreferences
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.request.AddSellerRequest
import com.emines_employee.model.response.UserResponse
import com.emines_employee.preference.reflection.ReflectionUtil
import com.emines_employee.util.Constants.PreferenceConstant.Companion.ADD_BUYER_DETAIL
import com.emines_employee.util.Constants.PreferenceConstant.Companion.USER_DETAIL

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/*
this class helps for storing and retrieve the small amount of data in device storage
*/
class PreferenceHelper( val sharedPref: SharedPreferences) : KoinComponent {
    private val reflectionUtil: ReflectionUtil by inject()
    fun put(key: String, value: String) {
        sharedPref.edit().putString(key, value).apply()
    }

    fun put(key: String, value: Boolean) {
        sharedPref.edit().putBoolean(key, value).apply()
    }

    fun put(key: String, value: Int) {
        sharedPref.edit().putInt(key, value).apply()
    }

    fun setUserDetail(UserResponse: UserResponse?) {
        val userDetail = reflectionUtil.parseClassToJson(UserResponse!!)

        sharedPref.edit().putString(USER_DETAIL, userDetail).apply()
    }

    fun getUserDetail(): UserResponse? {
        return reflectionUtil.parseJsonToClass(get(USER_DETAIL, "")!!, UserResponse::class.java)
    }

    operator fun get(key: String, defaultValue: String): String? {
        return sharedPref.getString(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Int): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    operator fun get(key: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun addBuyerDetail(addBuyer: AddBuyerRequest?) {
        val userDetail = reflectionUtil.parseClassToJson(addBuyer!!)
        sharedPref.edit().putString(ADD_BUYER_DETAIL, userDetail).apply()
    }

    fun getBuyerDetail(): AddBuyerRequest? {
        return reflectionUtil.parseJsonToClass(
            get(ADD_BUYER_DETAIL, "")!!,
            AddBuyerRequest::class.java
        )
    }


    fun addSellerDetail(addSeller: AddSellerRequest?) {
        val userDetail = reflectionUtil.parseClassToJson(addSeller!!)
        sharedPref.edit().putString(ADD_BUYER_DETAIL, userDetail).apply()
    }

    fun getSellerDetail(): AddSellerRequest? {
        return reflectionUtil.parseJsonToClass(
            get(ADD_BUYER_DETAIL, "")!!,
            AddSellerRequest::class.java
        )
    }

    fun clearSharedPref() {
        sharedPref.edit().clear().apply()
    }

}