package com.emines_employee.screen.dashboard.profile.kyc

import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityProfileKycBinding

class ProfileKYCActivity : BaseActivity() {
    private lateinit var profileBinding: ActivityProfileKycBinding
    private val userDetail = mPref.getUserDetail()

    override val layoutId = R.layout.activity_profile_kyc

    override fun onCreateInit(binding: ViewDataBinding?) {
        profileBinding = binding as ActivityProfileKycBinding
        profileBinding.apply {
            toolbarPKYC.tvToolBarTitle2.text = getString(R.string.buyer_kyc)
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
                etPanNumberPKYC.text = it.panNo
                Glide.with(this@ProfileKYCActivity).load(it.panFile).into(ivUploadPanPKYC)
                etAadhaarNumberKYC.text = it.panNo.toString()
                Glide.with(this@ProfileKYCActivity).load(it.panFile).into(ivUploadAadhaarKYC)
                etCancelChequeKYC.text = it.cancleCheque.toString()
                Glide.with(this@ProfileKYCActivity).load(it.cancleChequeFile).into(ivCancelChequeKYC)
            }

        }
    }
}