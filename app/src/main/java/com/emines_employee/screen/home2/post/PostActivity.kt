package com.emines_employee.screen.home2.post

import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityPostBinding

class PostActivity : BaseActivity() {
    private lateinit var postBinding: ActivityPostBinding
    private val userDetail = mPref.getUserDetail()

    override val layoutId: Int
        get() = R.layout.activity_post

    override fun onCreateInit(binding: ViewDataBinding?) {
        postBinding = binding as ActivityPostBinding
        postBinding.apply {
            tvSendPostBtn.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            userDetail?.let {
                Glide.with(this@PostActivity).load(it.profilePic).into(ivProfileHomeImg)
                tvPostProfileName.text = String.format("%s %s",it.name,it.lastName)
            }
        }
    }
}