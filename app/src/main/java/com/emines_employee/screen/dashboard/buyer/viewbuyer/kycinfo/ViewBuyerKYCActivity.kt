package com.emines_employee.screen.dashboard.buyer.viewbuyer.kycinfo

import android.app.Dialog
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.CustomDialogs
import com.emines_employee.CustomDialogs.showImageDialog
import com.emines_employee.CustomDialogs.showWebViewDialog
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityViewBuyerKYCBinding
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.screen.dashboard.buyer.viewbuyer.bankinfo.ViewBuyerBankActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.checkIsImageExtensions
import com.emines_employee.util.downloadFileFromUrl
import com.emines_employee.util.mToast
import com.emines_employee.util.serializable

class ViewBuyerKYCActivity : BaseActivity() {
    private lateinit var mBind: ActivityViewBuyerKYCBinding

    override val layoutId = R.layout.activity_view_buyer_k_y_c

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityViewBuyerKYCBinding
        val buyerDetail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(
                Constants.DefaultConstant.MODEL_DETAIL
            )


        mBind.apply {
            toolbarBBA.tvToolBarTitle2.text =
                String.format("%s %s", getString(R.string.kyc), "(${getString(R.string.buyer)})")
            buyerDetail?.let {
                etPanNo.setText(it.pan_no)
                etAadhaarNo.setText(it.aadhar_no)
                etGSTNo.setText(it.gst_no)

                etUploadPan.apply {
                    if (checkIsImageExtensions(it.pan_file)) {
                        Glide.with(this@ViewBuyerKYCActivity).load(it.pan_file).into(this)
                    } else {
                        Glide.with(this@ViewBuyerKYCActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(this)
                    }
                    setOnClickListener { click ->
                        if (it.pan_file.isEmpty()) {
                            mToast(context.getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }
                        if (checkIsImageExtensions(it.pan_file)) {
                            showImageDialog(
                                this@ViewBuyerKYCActivity,
                                it.pan_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            showWebViewDialog(
                                this@ViewBuyerKYCActivity,
                                it.pan_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        }
                    }

                }
                etUploadAadhaar.apply {
                    if (checkIsImageExtensions(it.aadhar_file)) {
                        Glide.with(this@ViewBuyerKYCActivity).load(it.aadhar_file).into(this)
                    } else {
                        Glide.with(this@ViewBuyerKYCActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(this)
                    }
                    setOnClickListener { click ->
                        if (it.aadhar_file.isEmpty()) {
                            mToast(context.getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }
                        if (checkIsImageExtensions(it.aadhar_file)) {
                            showImageDialog(
                                this@ViewBuyerKYCActivity,
                                it.aadhar_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            showWebViewDialog(
                                this@ViewBuyerKYCActivity,
                                it.aadhar_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        }
                    }

                }
                etUploadGST.apply {
                    if (checkIsImageExtensions(it.gst_file)) {
                        Glide.with(this@ViewBuyerKYCActivity).load(it.gst_file).into(this)
                    } else {
                        Glide.with(this@ViewBuyerKYCActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(this)
                    }
                    setOnClickListener { click ->
                        if (it.gst_file.isEmpty()) {
                            mToast(context.getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }
                        if (checkIsImageExtensions(it.gst_file)) {
                            showImageDialog(
                                this@ViewBuyerKYCActivity,
                                it.gst_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            showWebViewDialog(
                                this@ViewBuyerKYCActivity,
                                it.gst_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        }
                    }

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
                                ViewBuyerBankActivity::class.java,
                                Constants.DefaultConstant.BUNDLE_KEY,
                                b
                            )
                        }
                    }
                }

            }
        }
    }
}