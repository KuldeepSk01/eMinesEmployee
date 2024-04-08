package com.emines_employee.screen.dashboard.profile.kyc

import android.app.Dialog
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityProfileKycBinding
import com.emines_employee.util.Constants
import com.emines_employee.util.checkIsImageExtensions
import com.emines_employee.util.downloadFileFromUrl
import com.emines_employee.util.mToast

class ProfileKYCActivity : BaseActivity() {
    private lateinit var profileBinding: ActivityProfileKycBinding
    private val userDetail = mPref.getUserDetail()

    override val layoutId = R.layout.activity_profile_kyc

    override fun onCreateInit(binding: ViewDataBinding?) {
        profileBinding = binding as ActivityProfileKycBinding
        profileBinding.apply {
            toolbarPKYC.tvToolBarTitle2.text = getString(R.string.buyer_kyc)
            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.back_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.visibility = View.GONE

            }

            userDetail?.let {
                etPanNumberPKYC.text = it.panNo
                ivUploadPanPKYC.apply {
                    if (it.cancleChequeFile.isNullOrEmpty()) {
                        Glide.with(this@ProfileKYCActivity)
                            .load(Constants.ImagesUrl.NO_IMAGE_AVAILABLE).into(this)
                    } else {
                        if (checkIsImageExtensions(it.panFile)) {
                            Glide.with(this@ProfileKYCActivity).load(it.panFile).into(this)
                        } else {
                            Glide.with(this@ProfileKYCActivity)
                                .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                                .into(this)
                        }
                    }


                    setOnClickListener { click ->
                        if (it.panFile.isEmpty()) {
                            mToast(context.getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }
                        if (checkIsImageExtensions(it.panFile)) {
                            CustomDialogs.showImageDialog(
                                this@ProfileKYCActivity,
                                it.panFile,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            CustomDialogs.showWebViewDialog(
                                this@ProfileKYCActivity,
                                it.panFile,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        }
                    }
                }

//                etAadhaarNumberKYC.text = it.panNo.toString()
                Glide.with(this@ProfileKYCActivity).load(it.panFile).into(ivUploadAadhaarKYC)

                ivUploadAadhaarKYC.apply {
                    if (it.cancleChequeFile.isNullOrEmpty()) {
                        Glide.with(this@ProfileKYCActivity)
                            .load(Constants.ImagesUrl.NO_IMAGE_AVAILABLE).into(this)
                    } else {
                        if (checkIsImageExtensions(it.aadharFile)) {
                            Glide.with(this@ProfileKYCActivity).load(it.aadharFile).into(this)
                        } else {
                            Glide.with(this@ProfileKYCActivity)
                                .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                                .into(this)
                        }
                    }

                    setOnClickListener { click ->
                        if (it.aadharFile.isEmpty()) {
                            mToast(context.getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }
                        if (checkIsImageExtensions(it.aadharFile)) {
                            CustomDialogs.showImageDialog(
                                this@ProfileKYCActivity,
                                it.aadharFile,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            CustomDialogs.showWebViewDialog(
                                this@ProfileKYCActivity,
                                it.aadharFile,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        }
                    }
                }

                ivCancelChequeKYC.apply {

                    if (it.cancleChequeFile.isNullOrEmpty()) {
                        Glide.with(this@ProfileKYCActivity)
                            .load(Constants.ImagesUrl.NO_IMAGE_AVAILABLE).into(this)
                    } else {

                        if (checkIsImageExtensions(it.cancleChequeFile)) {
                            Glide.with(this@ProfileKYCActivity).load(it.cancleChequeFile).into(this)
                        } else {
                            Glide.with(this@ProfileKYCActivity)
                                .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                                .into(this)
                        }
                    }



                    setOnClickListener { click ->
                        if (it.cancleChequeFile.isEmpty()) {
                            mToast(context.getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }
                        if (checkIsImageExtensions(it.cancleChequeFile)) {
                            CustomDialogs.showImageDialog(
                                this@ProfileKYCActivity,
                                it.cancleChequeFile,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            CustomDialogs.showWebViewDialog(
                                this@ProfileKYCActivity,
                                it.cancleChequeFile,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        dialog.dismiss()
                                    }
                                }).show()
                        }
                    }
                }

                etCancelChequeKYC.text = it.cancleCheque.toString()
                // Glide.with(this@ProfileKYCActivity).load(it.cancleChequeFile).into(ivCancelChequeKYC)
            }

        }
    }
}