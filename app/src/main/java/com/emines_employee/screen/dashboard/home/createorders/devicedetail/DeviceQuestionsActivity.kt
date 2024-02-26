package com.emines_employee.screen.dashboard.home.createorders.devicedetail

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityDeviceQuestionsBinding

class DeviceQuestionsActivity : BaseActivity() {
    private lateinit var deviceQuesBinding: ActivityDeviceQuestionsBinding
    override val layoutId: Int
        get() = R.layout.activity_device_questions

    override fun onCreateInit(binding: ViewDataBinding?) {
        deviceQuesBinding = binding as ActivityDeviceQuestionsBinding
        deviceQuesBinding.apply {
            toolbarUploadImg.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                    text = getString(R.string.mode_detail_text)
                }            }
            llc1QuestionLayout1.setOnClickListener {
                setToggleQuestion(iv1QuestionLayout1)
            }
            llc2QuestionLayout1.setOnClickListener {
                setToggleQuestion(iv2QuestionLayout1)
            }
            llcTruck1QuestionLayout1.setOnClickListener {
                setToggleTruck(iv3QuestionLayout1)
            }
            llcTruck2QuestionLayout1.setOnClickListener {
                setToggleTruck(iv4QuestionLayout1)
            }


            bottomButtons.apply {
                tvFirstBtn.text = getString(R.string.skip_text)
                tvSecondBtn.text = getString(R.string.continue_text)
                tvFirstBtn.setOnClickListener {

                }
                tvSecondBtn.setOnClickListener {
                    launchActivity(DeviceQuestions2Activity::class.java)
                 /*   replaceFragment(
                        R.id.flMainContainer,
                        UploadStockImgDetrail2Fragment(),
                        UploadtStockImgDetailFragment::javaClass.name
                    )*/
                }
            }


        }

    }

    private fun setToggleQuestion(
        iv: AppCompatImageView
    ) {
        deviceQuesBinding.apply {
            iv1QuestionLayout1.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
            iv2QuestionLayout1.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
        }
        iv.background =
            ResourcesCompat.getDrawable(resources, R.drawable.tick, null)
    }

    private fun setToggleTruck(
        iv: AppCompatImageView
    ) {
        deviceQuesBinding.apply {
            iv3QuestionLayout1.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
            iv4QuestionLayout1.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
        }
        iv.background =
            ResourcesCompat.getDrawable(resources, R.drawable.tick, null)
    }

}