package com.emines_employee.screen.dashboard.seller.viewseller.company

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityViewBuyerCompanyBinding
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.screen.dashboard.buyer.createrequest.address.AddressListActivity
import com.emines_employee.screen.dashboard.buyer.viewbuyer.addressinfo.AddressInfoListActivity
import com.emines_employee.screen.dashboard.seller.viewseller.address.SellerAddressListActivity
import com.emines_employee.screen.dashboard.seller.viewseller.address.SellerEditAddressActivity
import com.emines_employee.screen.dashboard.seller.viewseller.kyc.ViewSellerKYCActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.serializable

class ViewSellerCompanyActivity : BaseActivity() {
    private lateinit var mBind: ActivityViewBuyerCompanyBinding
    override val layoutId = R.layout.activity_view_buyer_company

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityViewBuyerCompanyBinding
        val buyerDetail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(
                Constants.DefaultConstant.MODEL_DETAIL
            )
        mBind.apply {
            toolbarBBA.tvToolBarTitle2.text = String.format(
                "%s %s",
                getString(R.string.company_info),
                "(${getString(R.string.seller)})"
            )
            buyerDetail?.let {
                etCName.setText(it.company_name)
                etCEmail.setText(it.company_email)
                etCMobile.setText(it.company_phone)
                etCWebsite.setText(it.website)
            }

            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.back_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.apply {
                    text = getString(R.string.next_cap)
                    setOnClickListener {
                        val b = Bundle()
                        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, buyerDetail)
                        launchActivity(
                            ViewSellerKYCActivity::class.java,
                            Constants.DefaultConstant.BUNDLE_KEY,
                            b
                        )
                        /*launchActivity(
                            SellerAddressListActivity::class.java,
                            Constants.DefaultConstant.BUNDLE_KEY,
                            b
                        )*/
                    }
                }
            }
        }
    }
}