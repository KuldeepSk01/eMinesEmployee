package com.emines_employee.screen.dashboard.home.createorders.devicedetail

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityDeviceQuestions2Binding

class DeviceQuestions2Activity : BaseActivity() {
    private lateinit var deviceQuesBinding: ActivityDeviceQuestions2Binding
    override val layoutId: Int
        get() = R.layout.activity_device_questions2

    override fun onCreateInit(binding: ViewDataBinding?) {
        deviceQuesBinding = binding as ActivityDeviceQuestions2Binding
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
                llc1QuestionLayout2.setOnClickListener {
                    setToggleQuestion(iv1QuestionLayout2)
                }
                llc2QuestionLayout2.setOnClickListener {
                    setToggleQuestion(iv2QuestionLayout2)
                }


                bottomButtons.apply {
                    tvFirstBtn.text = getString(R.string.skip_text)
                    tvSecondBtn.text = getString(R.string.continue_text)
                    tvFirstBtn.setOnClickListener {

                    }
                    tvSecondBtn.setOnClickListener {
                        launchActivity(DeviceQuestions3Activity::class.java)
                    }
                }


            }

        }
    }

    private fun setToggleQuestion(
        iv: AppCompatImageView
    ) {
        deviceQuesBinding.apply {
            iv1QuestionLayout2.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
            iv2QuestionLayout2.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
        }
        iv.background =
            ResourcesCompat.getDrawable(resources, R.drawable.tick, null)
    }


}