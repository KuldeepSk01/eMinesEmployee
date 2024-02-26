package com.emines_employee.screen.dashboard.seller.addseller.contactinfo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.databinding.ActivityAddSellerContactBinding
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.request.AddSellerRequest
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.addbuyer.bankinfo.EMAIL_EXIST
import com.emines_employee.screen.dashboard.buyer.addbuyer.bankinfo.PHONE_EXIST
import com.emines_employee.screen.dashboard.seller.SellerViewModel
import com.emines_employee.screen.dashboard.seller.addseller.companyinfo.AddSellerCompanyActivity
import com.emines_employee.util.IsValidBuyerSellerField
import com.emines_employee.util.OnDropDownListener
import com.emines_employee.util.checkFileSize
import com.emines_employee.util.compressImageFilePath
import com.emines_employee.util.dropDownPopup
import com.emines_employee.util.getRealPathFromURI
import com.emines_employee.util.isConnectionAvailable
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.showSettingsDialog
import com.emines_employee.util.validation.ValidationResult
import com.emines_employee.util.validation.ValidationState
import com.emines_employee.util.verifyCameraPermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.core.component.inject
import java.io.File

class AddSellerContactActivity : BaseActivity() {
    private lateinit var mBind: ActivityAddSellerContactBinding
    private val mViewModel: SellerViewModel by inject()
    private var profileImage: String? = null


    override val layoutId = R.layout.activity_add_seller_contact
    override fun onResume() {
        super.onResume()
        /* mPref.getBuyerDetail()?.let {
             mBind.apply {
                 etBuyerType.text =
                     if (it.buyer_type != null) it.buyer_type else getString(R.string.corporate)
                 etFullName.setText(it.full_name)
                 etDEmail.setText(it.email)
                 etDMobile.setText(it.phone)
             }
         }*/

    }

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAddSellerContactBinding
        mBind.apply {
            mPref.getSellerDetail()?.let {
                mBind.apply {
                    etSellerType.text =
                        if (it.seller_type != null) it.seller_type else getString(R.string.corporate)
                    etFullName.setText(it.full_name)
                    etDEmail.setText(it.email)
                    etDMobile.setText(it.phone)
                }
            }

            toolbarSellerContact.tvToolBarTitle2.text = String.format(
                "%s %s",
                getString(R.string.contact_person_common),
                "(${getString(R.string.seller)})"
            )

            etSellerType.setOnClickListener {
                dropDownPopup(
                    this@AddSellerContactActivity,
                    it,
                    R.menu.menu_types_of_buyer,
                    object : OnDropDownListener {
                        override fun onDropDownClick(item: String) {
                            etSellerType.text = item
                        }
                    }).show()
            }

            etSellerProfileText.setOnClickListener {
                try {
                    CustomDialogs.showSelectProfileBottomSheetDialog(this@AddSellerContactActivity,
                        object : CustomDialogs.OnBottomSheetClickListener {
                            override fun onImageClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                                    mLog("PERMISSION_GRANTED 13")
                                    gallaryLauncher.launch(gallery)
                                } else {
                                    if (verifyCameraPermission(this@AddSellerContactActivity)) {
                                        val gallery = Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI
                                        )
                                        mLog("setInitialSetup: PERMISSION_GRANTED below 13")
                                        gallaryLauncher.launch(gallery)
                                    } else {
                                        showSettingsDialog(this@AddSellerContactActivity)
                                    }
                                }
                                dialog.dismiss()
                            }

                            override fun onFileClick(dialog: BottomSheetDialog) {
                                dialog.dismiss()
                            }

                            override fun onCameraClick(dialog: BottomSheetDialog) {
                                if (verifyCameraPermission(this@AddSellerContactActivity)) {
                                    cameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                                } else {
                                    showSettingsDialog(this@AddSellerContactActivity)
                                }

                                dialog.dismiss()
                            }

                        })
                } catch (e: Exception) {
                    mLog("Select or Capture Exception")
                }


                // pickImage()
            }

            bottomButtons.apply {
                tvFirstBtn.visibility = View.GONE
                tvSecondBtn.apply {
                    text = getString(R.string.next_cap)
                    setOnClickListener {
                        mViewModel.isValidData(
                            etSellerType.text.toString(),
                            etFullName.text.toString(),
                            etDEmail.text.toString(),
                            etDMobile.text.toString()
                        )
                        mViewModel.validationResponseObserver.observe(
                            this@AddSellerContactActivity,
                            validationObserver
                        )

                    }
                }

            }
        }
    }


    private val validationObserver: Observer<ValidationState> by lazy {
        Observer {
            mBind.apply {
                when (it.msg) {
                    ValidationResult.EMPTY_BUYER_TYPE -> {
                        etSellerType.error = getString(it.code)
                        etSellerType.requestFocus()
                    }

                    ValidationResult.EMPTY_FULL_NAME -> {
                        etFullName.error = getString(it.code)
                        etFullName.requestFocus()
                    }

                    ValidationResult.EMPTY_EMAIL, ValidationResult.INVALID_EMAIL -> {
                        etDEmail.error = getString(it.code)
                        etDEmail.requestFocus()
                    }

                    ValidationResult.EMPTY_MOBILE_NUMBER, ValidationResult.VALID_MOBILE_NUMBER -> {
                        etDMobile.error = getString(it.code)
                        etDMobile.requestFocus()
                    }

                    ValidationResult.SUCCESS -> {
                        if (!isConnectionAvailable()) {
                            mToast("Internet not available")
                            return@Observer
                        } else {
                            if (IsValidBuyerSellerField) {
                                mPref.getSellerDetail()?.let {
                                    val addSellerRes = AddSellerRequest().apply {
                                        userId = mPref.getUserDetail()?.id!!
                                        seller_type = etSellerType.text.toString()
                                        full_name = etFullName.text.toString()
                                        email = etDEmail.text.toString()
                                        phone = etDMobile.text.toString()
                                        company_name = it.company_name
                                        company_email = it.company_email
                                        company_phone = it.company_phone
                                        website = it.website
                                        pan_no = it.pan_no
                                        aadhar_no = it.aadhar_no
                                        gst_no = it.gst_no
                                        pan_file_path = it.pan_file_path
                                        aadhar_file_path = it.aadhar_file_path
                                        gst_file_path = it.gst_file_path
                                        profile_image = it.profile_image
                                        bank_name = it.bank_name
                                        ifsc_code = it.ifsc_code
                                        account_no = it.account_no

                                    }
                                    mPref.addSellerDetail(addSellerRes)
                                }
                                val req = mPref.getSellerDetail()

                                mViewModel.hitSaveSellerApi(req!!)
                                mViewModel.getSaveSellerResponse()
                                    .observe(
                                        this@AddSellerContactActivity,
                                        saveBuyerResponseObserver
                                    )
                                mLog("Seller Detail is $req")
                            } else {
                                mLog("IsValidSellerField false")
                                val addSellerRes = AddSellerRequest().apply {
                                    seller_type = etSellerType.text.toString()
                                    full_name = etFullName.text.toString()
                                    email = etDEmail.text.toString()
                                    phone = etDMobile.text.toString()
                                    profile_image = profileImage
                                }
                                mPref.addSellerDetail(addSellerRes)
                                launchActivity(AddSellerCompanyActivity::class.java)

                            }

                        }
                    }

                    else -> {}
                }
            }
        }
    }


    private val saveBuyerResponseObserver: Observer<ApiResponse<SuccessMsgResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mToast(it.data?.message.toString())
                    mPref.addBuyerDetail(AddBuyerRequest())
                    finish()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    val err = it.error?.message.toString()
                    mToast(err)
                    if (err == PHONE_EXIST || err == EMAIL_EXIST) {
                        IsValidBuyerSellerField = true
                    }
                }
            }
        }
    }

    private val gallaryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val uri = result.data?.data!!
                    Log.d("MyTag", "data $uri")
                    mBind.ivSellerInfoProfile.apply {
                        visibility = View.VISIBLE
                        setImageURI(uri)
                    }
                    profileImage =
                        getRealPathFromURI(uri!!, this@AddSellerContactActivity).toString()
                }
            } catch (e: Exception) {
                profileImage = null
                mBind.ivSellerInfoProfile.visibility = View.GONE
                mLog("pan selected file error")
            }

        }


    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val b = data?.extras?.get("data") as Bitmap
                    val path = compressImageFilePath(b, this@AddSellerContactActivity)

                    if (checkFileSize(File(path))!! < 2.toDouble()) {
                        profileImage = path
                        mBind.ivSellerInfoProfile.setImageBitmap(b)
                        mLog("Image path : $profileImage")
                    } else {
                        mToast(getString(R.string.image_should_be_less_then_or_equal_2mb))
                    }
                }
            } catch (e: Exception) {
                mLog("Nothing Selected Image")
                profileImage = null
            }

        }


}