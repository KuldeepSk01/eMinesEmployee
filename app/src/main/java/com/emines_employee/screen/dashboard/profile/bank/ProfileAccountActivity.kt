package com.emines_employee.screen.dashboard.profile.bank

import android.view.View
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityProfileBankDetailBinding

class ProfileAccountActivity : BaseActivity() {
    private lateinit var profileBinding: ActivityProfileBankDetailBinding
    private val userDetail = mPref.getUserDetail()

    override val layoutId = R.layout.activity_profile_bank_detail

    override fun onCreateInit(binding: ViewDataBinding?) {
        profileBinding = binding as ActivityProfileBankDetailBinding
        profileBinding.apply {
            toolbarPBank.tvToolBarTitle2.text = getString(R.string.bank_detail_text)
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
                etAccountNamePBank.text = it.accountName.toString()
                etAccountNumberPBank.text = it.accountNumber.toString()
                etAccountBranchPBank.text = it.branchName.toString()
                etIFSCPBank.text = it.ifscCode.toString()
            }
        }
    }
}