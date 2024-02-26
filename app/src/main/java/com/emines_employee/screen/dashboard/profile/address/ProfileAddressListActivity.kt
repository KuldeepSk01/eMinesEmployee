package com.emines_employee.screen.dashboard.profile.address

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.AddressAdapter
import com.emines_employee.adapter.listener.AdapterAddressListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityProfileAddressListBinding
import com.emines_employee.model.UserAddress
import com.emines_employee.util.Constants

class ProfileAddressListActivity : BaseActivity(), AdapterAddressListener {
    private lateinit var buyerBankAccountBinding: ActivityProfileAddressListBinding
    private val userDetail = mPref.getUserDetail()
    private lateinit var userAddress: UserAddress


    override val layoutId = R.layout.activity_profile_address_list

    override fun onCreateInit(binding: ViewDataBinding?) {
        buyerBankAccountBinding = binding as ActivityProfileAddressListBinding
        buyerBankAccountBinding.apply {
            toolbarAddressList.tvToolBarTitle2.text = getString(R.string.select_address)

            rvAddressPAList.apply {
                layoutManager = LinearLayoutManager(
                    this@ProfileAddressListActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = AddressAdapter(
                    getAddressList(),
                    this@ProfileAddressListActivity,
                    this@ProfileAddressListActivity
                )
            }

            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.back_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.visibility = View.GONE
            }
        }
    }

    override fun onAddressSelect(address: UserAddress) {
        userAddress = address
    }

    override fun onEditAddress(address: UserAddress) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, address)
        launchActivity(ProfileAddressActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)
    }

    private fun getAddressList(): MutableList<UserAddress> {
        val list = mutableListOf<UserAddress>()
        userDetail?.let {
            if (!it.currentCityName.isNullOrEmpty()){
                list.add(
                    UserAddress(
                        1,
                        "Current Address",
                        it.currentCountryName,
                        it.currentStateName,
                        it.currentCityName,
                        it.currentArea,
                        it.currentPin,
                        it.currentAddress
                    )
                )
            }
            if (!it.permanentCityName.isNullOrEmpty()){
                list.add(
                    UserAddress(
                        2,
                        "Permanent Address",
                        it.permanentCountryName,
                        it.permanentStateName,
                        it.permanentCityName,
                        it.permanentArea,
                        it.permanentPin,
                        it.permanentAddress

                    )
                )
            }

        }
        return list
    }

}