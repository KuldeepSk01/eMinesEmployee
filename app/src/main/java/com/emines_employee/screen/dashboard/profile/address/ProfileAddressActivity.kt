package com.emines_employee.screen.dashboard.profile.address

import android.view.View
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityProfileAddressBinding
import com.emines_employee.model.UserAddress
import com.emines_employee.util.Constants
import com.emines_employee.util.serializable

class ProfileAddressActivity : BaseActivity() {
    private lateinit var profileBinding: ActivityProfileAddressBinding
    private lateinit var userAddress: UserAddress

    override val layoutId = R.layout.activity_profile_address

    override fun onCreateInit(binding: ViewDataBinding?) {
        profileBinding = binding as ActivityProfileAddressBinding
        userAddress = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)?.serializable<UserAddress>(Constants.DefaultConstant.MODEL_DETAIL)!!
        profileBinding.apply {
            toolbarPA.tvToolBarTitle2.text = userAddress.type
            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.back_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.visibility=View.GONE
                userAddress.let {
                    //tvTypePA.text= it.type
                    etCountryPA.text = it.country
                    etStatePA.text = it.state
                    etAreaPA.text = it.area
                    etCityPA.text = it.city
                    etPinCodePA.text = it.piccode
                    etAddressPA.text = it.address
                }
            }
        }
    }
}