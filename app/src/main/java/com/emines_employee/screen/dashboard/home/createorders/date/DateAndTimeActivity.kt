package com.emines_employee.screen.dashboard.home.createorders.date

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityDateAndTimeBinding

class DateAndTimeActivity : BaseActivity() {
    private lateinit var dateBinding: ActivityDateAndTimeBinding
    var selectText = ""


    override val layoutId: Int
        get() = R.layout.activity_date_and_time

    override fun onCreateInit(binding: ViewDataBinding?) {
        dateBinding = binding as ActivityDateAndTimeBinding
        dateBinding.apply {

            toolbarDateTime.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources,R.color.white,null))
                    text = getString(R.string.pick_up_date_amp_time_text)
                }            }

            llcPickDate.setOnClickListener {
                selectText = getString(R.string.within_12_hours_text)
                setIsCheck(iv1)
            }
            llcPickDateQ2.setOnClickListener {
                selectText = getString(R.string.within_48_hours_text)
                setIsCheck(iv2)
            }
            llcPickDateQ3.setOnClickListener {
                selectText = getString(R.string.other)
                setIsCheck(iv3)
            }

            bottomButtons.apply {
                tvFirstBtn.text = getString(R.string.skip_text)
                tvSecondBtn.text = getString(R.string.continue_text)
                tvFirstBtn.setOnClickListener {

                }
                tvSecondBtn.setOnClickListener {
                    if (selectText == getString(R.string.other)) {
                      launchActivity(DateAndTimeOtherActivity::class.java)
                    } else {
                        launchActivity(DateAndTimeOtherActivity::class.java)
                    }

                }
            }

        }
    }

    private fun setIsCheck(
        iv: AppCompatImageView
    ) {
        dateBinding.apply {
            iv1.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
            iv2.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
            iv3.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_checkbox, null)
        }
        iv.background =
            ResourcesCompat.getDrawable(resources, R.drawable.tick, null)
    }
}