package com.emines_employee.screen.onboarding

import com.emines_employee.R
import com.emines_employee.base.BaseViewModel
import com.emines_employee.model.CommonModel

class OnBoardViewModel:BaseViewModel() {
    var onBoardingActivity:OnBoardingActivity?=null

    fun getOnBoardList(): MutableList<CommonModel> {
        val list = mutableListOf<CommonModel>()
        list.add(
            CommonModel(
                R.drawable.loading_one, onBoardingActivity!!.getString(R.string.loading_one_text))
        )
        list.add(
            CommonModel(
                R.drawable.loading_two,
                onBoardingActivity!!.getString(R.string.loading_one_text)            )
        )
        list.add(
            CommonModel(
                R.drawable.loading_three,
                onBoardingActivity!!.getString(R.string.loading_one_text)            )
        )
        return list
    }

}