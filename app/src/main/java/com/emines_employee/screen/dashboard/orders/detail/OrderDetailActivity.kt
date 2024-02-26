package com.emines_employee.screen.dashboard.orders.detail

import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : BaseActivity() {
    private lateinit var orderDetailBinding : ActivityOrderDetailBinding
    override val layoutId: Int
        get() = R.layout.activity_order_detail

    override fun onCreateInit(binding: ViewDataBinding?) {
        orderDetailBinding = binding as ActivityOrderDetailBinding
        orderDetailBinding.apply {
            toolBarOrderDetail.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.order_detail)
            }

            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.cancel_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.apply {
                    text = getString(R.string.confirm)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }

            }

        }
    }
}