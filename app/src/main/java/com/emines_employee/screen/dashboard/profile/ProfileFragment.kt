package com.emines_employee.screen.dashboard.profile

import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseFragment
import com.emines_employee.databinding.FragmentProfileBinding
import com.emines_employee.screen.dashboard.home.attendance.AttendanceSelectDateActivity
import com.emines_employee.screen.dashboard.profile.address.ProfileAddressActivity
import com.emines_employee.screen.dashboard.profile.address.ProfileAddressListActivity
import com.emines_employee.screen.dashboard.profile.bank.ProfileAccountActivity
import com.emines_employee.screen.dashboard.profile.kyc.ProfileKYCActivity
import com.emines_employee.screen.dashboard.profile.profile.ProfileInfoActivity
import com.emines_employee.screen.home2.post.PostActivity
import com.emines_employee.screen.login.LoginActivity
import com.emines_employee.util.mLog

class ProfileFragment : BaseFragment() {
    private lateinit var profileFragment: FragmentProfileBinding
    private val userDetail = mPref.getUserDetail()


    override fun getLayoutId() = R.layout.fragment_profile
    override fun onCreateViewInit(binding: ViewDataBinding, view: View) {
        profileFragment = binding as FragmentProfileBinding
        profileFragment.apply {
            tvProfile.setOnClickListener {
                launchActivity(ProfileInfoActivity::class.java)
            }
            tvAddressProfile.setOnClickListener {
                launchActivity(ProfileAddressListActivity::class.java)
            }
            tvKYCProfile.setOnClickListener {
                launchActivity(ProfileKYCActivity::class.java)
            }
            tvBankDetailProfile.setOnClickListener {
                launchActivity(ProfileAccountActivity::class.java)
            }
            tvAttendanceProfile.setOnClickListener {
                launchActivity(AttendanceSelectDateActivity::class.java)
            }

            tvLogoutProfile.setOnClickListener {
                mPref.clearSharedPref()
                launchActivity(LoginActivity::class.java)
                requireActivity().finish()
            }

            userDetail?.let {
                mLog("User Profile : $it")
                Glide.with(requireContext()).load(it.profilePic).placeholder(R.drawable.profile_img).into(ivProfileImg)
                tvProfileName.text = String.format("%s %s",it.name,it.lastName)
                tvProfileMobile.text = it.phone
                tvProfileEmail.text = it.email
            }


        }

    }

}