package com.emines_employee.screen.dashboard.home.createorders.devicedetail

import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityDeviceQuestionsBinding
import com.emines_employee.screen.dashboard.home.createorders.date.DateAndTimeActivity

class DeviceQuestions3Activity : BaseActivity() {
    private lateinit var deviceQuesBinding: ActivityDeviceQuestionsBinding
    override val layoutId: Int
        get() = R.layout.activity_device_questions

    override fun onCreateInit(binding: ViewDataBinding?) {
        deviceQuesBinding = binding as ActivityDeviceQuestionsBinding
        deviceQuesBinding.apply {
            toolbarUploadImg.apply {
                ivToolBarBack.apply {
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                    tvToolBarTitle.apply {
                        setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                        text = getString(R.string.mode_detail_text)
                    }
                }
                bottomButtons.apply {
                    tvFirstBtn.text = getString(R.string.skip_text)
                    tvSecondBtn.text = getString(R.string.continue_text)
                    tvFirstBtn.setOnClickListener {
                        launchActivity(DateAndTimeActivity::class.java)
                    }
                    tvSecondBtn.setOnClickListener {
                        launchActivity(DateAndTimeActivity::class.java)

                    }
                }
            }

        }
    }
}