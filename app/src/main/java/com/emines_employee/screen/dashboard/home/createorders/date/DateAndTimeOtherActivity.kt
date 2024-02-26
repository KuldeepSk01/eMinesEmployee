package com.emines_employee.screen.dashboard.home.createorders.date

import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityDateAndTimeOtherBinding
import com.emines_employee.screen.dashboard.home.createorders.payment.PaymentActivity

class DateAndTimeOtherActivity : BaseActivity() {
    private lateinit var dateBinding: ActivityDateAndTimeOtherBinding

    override val layoutId: Int
        get() = R.layout.activity_date_and_time_other

    override fun onCreateInit(binding: ViewDataBinding?) {
        dateBinding = binding as ActivityDateAndTimeOtherBinding
        dateBinding.apply {
            toolbarDateTime.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources,R.color.white,null))
                    text = getString(R.string.pick_up_date_amp_time_text)
                }
            }
            bottomButtons.apply {
                tvFirstBtn.text = getString(R.string.skip_text)
                tvSecondBtn.text = getString(R.string.continue_text)
                tvFirstBtn.setOnClickListener {
                    launchActivity(PaymentActivity::class.java)
                }
                tvSecondBtn.setOnClickListener {
                    launchActivity(PaymentActivity::class.java)
                }
            }
            llcMorningDay.setOnClickListener {
                setBtnBackGround(it as LinearLayoutCompat,tvMorningSDO)
            }

            llcDaySDO.setOnClickListener {
                setBtnBackGround(it as LinearLayoutCompat,tvDaySDO)
            }

            llcEveningSDO.setOnClickListener {
                setBtnBackGround(it as LinearLayoutCompat,tvEveningSDO)
            }

            llcNightSDO.setOnClickListener {
                setBtnBackGround(it as LinearLayoutCompat,tvNightSDO)
            }

            llcEveningNight.setOnClickListener {
                setBtnBackGround(it as LinearLayoutCompat,tvEveningSDO)
            }
            llcAnytimeSDO.setOnClickListener {
                setBtnBackGround(it as LinearLayoutCompat,tvAnytimeSDO)
            }

        }

    }

    private fun setBtnBackGround(
        llc: LinearLayoutCompat,
        tv: AppCompatTextView

    ) {
        dateBinding.apply {
            llcMorningDay.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_bg_drawable, null)
            llcDaySDO.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_bg_drawable, null)
            llcEveningSDO.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_bg_drawable, null)
            llcNightSDO.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_bg_drawable, null)
            llcAnytimeSDO.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_bg_drawable, null)

            tvMorningSDO.setTextColor(resources.getColor(R.color.hint_text_color, null))
            tvDaySDO.setTextColor(resources.getColor(R.color.hint_text_color, null))
            tvEveningSDO.setTextColor(resources.getColor(R.color.hint_text_color, null))
            tvNightSDO.setTextColor(resources.getColor(R.color.hint_text_color, null))
            tvAnytimeSDO.setTextColor(resources.getColor(R.color.hint_text_color, null))
        }
        tv.setTextColor(resources.getColor(R.color.white, null))
        llc.background =
            ResourcesCompat.getDrawable(resources, R.drawable.schedule_pickup_btn_drawable, null)
    }

}