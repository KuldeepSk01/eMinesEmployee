package com.emines_employee.screen.verifyme

import androidx.activity.OnBackPressedCallback
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityVerifyMeBinding
import com.emines_employee.screen.login.LoginActivity
import com.emines_employee.screen.verifyme2.VerifyMe2Activity
import org.koin.core.component.inject

class VerifyMeActivity : BaseActivity() {

    private lateinit var verifyBinding: ActivityVerifyMeBinding
    private val mViewModel: VerifyMeViewModel by inject()
    private val userDetail = mPref.getUserDetail()


    override val layoutId: Int
        get() = R.layout.activity_verify_me

    override fun onCreateInit(binding: ViewDataBinding?) {
        verifyBinding = binding as ActivityVerifyMeBinding

        verifyBinding.apply {
            onBackPressedDispatcher.addCallback(this@VerifyMeActivity,object:OnBackPressedCallback(
                true
            ){
                override fun handleOnBackPressed() {
                    finishAffinity()
                }
            })


            toolbarVerifyMe.apply {
                ivToolBarBack.setOnClickListener {
                 /*   launchActivity(LoginActivity::class.java)
                    finish()*/
                    finishAffinity()
                   // onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.verify_text)
            }

            userDetail?.let {
                Glide.with(this@VerifyMeActivity).load(it.profilePic).placeholder(R.drawable.profile_img).into(ivVerifyImg1)
                tvVerifyProfileTitle.text =
                    String.format("%s %s \n\n %s", it.name, it.lastName, it.currentCity)
            }

            tvVerifyProfileConfirmBtn.setOnClickListener {
                launchActivity(VerifyMe2Activity::class.java)
                //finish()
            }
            tvVerifyProfileNotMeBtn.setOnClickListener {
                mPref.clearSharedPref()
                launchActivity(LoginActivity::class.java)
               // finish()
            }
        }


    }
}