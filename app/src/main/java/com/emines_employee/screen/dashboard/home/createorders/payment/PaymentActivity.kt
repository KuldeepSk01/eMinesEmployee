package com.emines_employee.screen.dashboard.home.createorders.payment

import android.app.Dialog
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.emines_employee.CustomDialogs
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityPaymentBinding

class PaymentActivity : BaseActivity() {
    private lateinit var paymentBinding: ActivityPaymentBinding

    override val layoutId: Int
        get() = R.layout.activity_payment

    override fun onCreateInit(binding: ViewDataBinding?) {
        paymentBinding = binding as ActivityPaymentBinding
        paymentBinding.apply {
            toolbarPayment.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources,R.color.white,null))
                    text = getString(R.string.payments_text)
                }
            }
            bottomButtons.apply {
                tvSecondBtn.text = getString(R.string.continue_text)
                tvFirstBtn.visibility = View.GONE
                tvSecondBtn.setOnClickListener {
                   /* CustomDialogs.showCustomSuccessDialog(this@PaymentActivity,
                        getString(R.string.confirmed_text),object : CustomDialogs.CustomDialogsListener{
                            override fun onComplete(d: Dialog) {
                                launchActivity(MainActivity::class.java)
                                d.dismiss()
                            }
                        }).show()*/
                }
            }
        }
    }
}