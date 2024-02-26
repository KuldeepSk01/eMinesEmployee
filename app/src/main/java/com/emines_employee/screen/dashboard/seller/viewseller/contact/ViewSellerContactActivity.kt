package com.emines_employee.screen.dashboard.seller.viewseller.contact

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityViewBuyerContactBinding
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.screen.dashboard.buyer.viewbuyer.compayinfo.ViewBuyerCompanyActivity
import com.emines_employee.screen.dashboard.seller.viewseller.company.ViewSellerCompanyActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.downloadFileFromUrl
import com.emines_employee.util.serializable

class ViewSellerContactActivity : BaseActivity() {
    private lateinit var mBind: ActivityViewBuyerContactBinding
    override val layoutId = R.layout.activity_view_buyer_contact

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityViewBuyerContactBinding
        val buyerDetail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(Constants.DefaultConstant.MODEL_DETAIL)

        mBind.apply {
            toolbarBBA.tvToolBarTitle2.text = String.format(
                "%s %s",
                getString(R.string.contact_person_common),
                "(${getString(R.string.seller)})"
            )
            buyerDetail?.let {
                etBuyerType.text = it.seller_type
                etFullName.setText(it.name)
                etDEmail.setText(it.email)
                etDMobile.setText(it.phone)
                if (!it.profile_pic.isNullOrEmpty()){
                    etViewProfile.apply {
                        Glide.with(this@ViewSellerContactActivity).load(it.profile_pic).into(this)
                        setOnClickListener {view->

                            CustomDialogs.showImageDialog(
                                this@ViewSellerContactActivity,
                                it.profile_pic,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        }
                    }
                }else{
                    etViewProfile.visibility = View.GONE
                    tvProfile.visibility = View.GONE
                }
            }

            bottomButtons.apply {
                tvFirstBtn.visibility = View.GONE
                tvSecondBtn.apply {
                    text = getString(R.string.next_cap)
                    setOnClickListener {
                        val b = Bundle()
                        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, buyerDetail)
                        launchActivity(
                            ViewSellerCompanyActivity::class.java,
                            Constants.DefaultConstant.BUNDLE_KEY,
                            b
                        )
                    }
                }

            }
        }
    }

}