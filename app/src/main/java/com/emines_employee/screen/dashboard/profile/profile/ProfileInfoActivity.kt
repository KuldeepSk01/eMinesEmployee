package com.emines_employee.screen.dashboard.profile.profile

import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityProfileInfoBinding

class ProfileInfoActivity : BaseActivity() {
    private lateinit var profileBinding: ActivityProfileInfoBinding
    private val userDetail = mPref.getUserDetail()

    override val layoutId = R.layout.activity_profile_info

    override fun onCreateInit(binding: ViewDataBinding?) {
        profileBinding = binding as ActivityProfileInfoBinding
        profileBinding.apply {
            toolbarBBA.tvToolBarTitle2.text = getString(R.string.profile_info)
            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.back_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.visibility = View.GONE
            }

            userDetail?.let {
                etFNamePI.text = it.name
                etLNamePI.text = it.lastName
                etEmailPI.text = it.email
                etPhoneNumberPI.text = it.phone
                Glide.with(this@ProfileInfoActivity).load(it.profilePic).placeholder(R.drawable.profile_img).into(ivPI)

                /*if (it.profilePic.isNullOrEmpty()){
                    Glide.with(this@ProfileInfoActivity).load(it.profilePic).into(ivPI)
                }else{
                }*/
            }

        }
    }


}