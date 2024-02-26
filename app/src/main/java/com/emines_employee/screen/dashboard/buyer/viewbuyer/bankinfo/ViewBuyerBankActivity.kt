package com.emines_employee.screen.dashboard.buyer.viewbuyer.bankinfo

import android.view.View
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityViewBuyerBankBinding
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.screen.dashboard.buyer.BuyersViewModel
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.serializable
import org.koin.core.component.inject

class ViewBuyerBankActivity : BaseActivity() {
    private lateinit var mBind: ActivityViewBuyerBankBinding
    private val mViewModel: BuyersViewModel by inject()

    override val layoutId = R.layout.activity_view_buyer_bank

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityViewBuyerBankBinding
        val buyerDetail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(
                Constants.DefaultConstant.MODEL_DETAIL
            )

        mBind.apply {
            toolbarBuyer.tvToolBarTitle2.text = String.format("%s %s",getString(R.string.bank_account),"(${getString(R.string.buyer)})")

            buyerDetail?.let {
                etBankName.setText(it.bank_name)
                etAccountNo.setText(it.account_no)
                etIFSCCODE.setText(it.ifsc_code)
            }
            mLog(buyerDetail.toString())

            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.back_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                if (buyerDetail?.isEditBuyer!!) {
                    tvSecondBtn.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.save_cap)
                        setOnClickListener {
                            launchActivity(MainActivity::class.java)
                            finish()
                        }
                    }

                } else {
                    tvSecondBtn.apply {
                        visibility = View.VISIBLE
                        text = getString(R.string.home)
                        setOnClickListener {
                            launchActivity(MainActivity::class.java)
                            finish()
                        }
                    }

                  //  tvSecondBtn.visibility = View.GONE
                }
            }
        }
    }


}
