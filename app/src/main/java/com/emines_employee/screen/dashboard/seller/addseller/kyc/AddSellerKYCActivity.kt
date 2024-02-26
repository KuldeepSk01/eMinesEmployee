package com.emines_employee.screen.dashboard.seller.addseller.kyc

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityAddSellerKycBinding
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.request.AddSellerRequest
import com.emines_employee.screen.dashboard.buyer.addbuyer.bankinfo.AddBuyerBankActivity
import com.emines_employee.screen.dashboard.seller.addseller.bankinfo.AddSellerBankActivity
import com.emines_employee.util.checkFileSize
import com.emines_employee.util.compressImageFilePath
import com.emines_employee.util.getRealPathFromURI
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.showSettingsDialog
import com.emines_employee.util.verifyCameraPermission
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File

class AddSellerKYCActivity : BaseActivity() {
    private lateinit var mBind: ActivityAddSellerKycBinding
    override val layoutId = R.layout.activity_add_seller_kyc
    private var aadhaarImage: String? = null
    private var panCardImage: String? = null
    private var gstImage: String? = null
    private var environmentImage: String? = null
    private var cancelledChequeImage: String? = null

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAddSellerKycBinding

        mBind.apply {
            toolbarSellerKYC.tvToolBarTitle2.text =
                String.format("%s %s", getString(R.string.kyc), "(${getString(R.string.seller)})")
            etUploadSellerPan.setOnClickListener {
                try {
                    CustomDialogs.showBottomSheetDialog(this@AddSellerKYCActivity,
                        object : CustomDialogs.OnBottomSheetClickListener {
                            override fun onImageClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                                    mLog("PERMISSION_GRANTED 13")
                                    selectPANImageLauncher.launch(gallery)
                                } else {
                                    if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                        val gallery = Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI
                                        )
                                        mLog("setInitialSetup: PERMISSION_GRANTED below 13")
                                        selectPANImageLauncher.launch(gallery)
                                    } else {
                                        showSettingsDialog(this@AddSellerKYCActivity)
                                        //mToast("Please enable required permission")
                                    }

                                }

                                dialog.dismiss()
                            }

                            override fun onFileClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                    pdfIntent.type = "application/pdf"
                                    pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                    pdfPanActivityLauncher.launch(
                                        Intent.createChooser(
                                            pdfIntent,
                                            "Select PDF file"
                                        )
                                    )

                                } else {
                                    if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                        pdfIntent.type = "application/pdf"
                                        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                        pdfPanActivityLauncher.launch(
                                            Intent.createChooser(
                                                pdfIntent,
                                                "Select PDF file"
                                            )
                                        )
                                    } else {
                                        showSettingsDialog(this@AddSellerKYCActivity)
                                    }
                                }

                                dialog.dismiss()
                            }

                            override fun onCameraClick(dialog: BottomSheetDialog) {
                                if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                    cameraLauncherPan.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                                } else {
                                    showSettingsDialog(this@AddSellerKYCActivity)
                                }

                                dialog.dismiss()
                            }

                        })
                } catch (e: Exception) {
                    mLog("Select or Capture Exception")
                }

            }

            etUploadSellerAadhaarNo.setOnClickListener {

                try {
                    CustomDialogs.showBottomSheetDialog(this@AddSellerKYCActivity,
                        object : CustomDialogs.OnBottomSheetClickListener {
                            override fun onImageClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                                    mLog("PERMISSION_GRANTED 13")
                                    selectAadhaarImageLauncher.launch(gallery)
                                } else {
                                    if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                        val gallery = Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI
                                        )
                                        mLog("setInitialSetup: PERMISSION_GRANTED below 13")
                                        selectAadhaarImageLauncher.launch(gallery)
                                    } else {
                                        showSettingsDialog(this@AddSellerKYCActivity)
                                    }
                                }

                                dialog.dismiss()
                            }

                            override fun onFileClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                    pdfIntent.type = "application/pdf"
                                    pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                    pdfAadhaarLauncher.launch(
                                        Intent.createChooser(
                                            pdfIntent,
                                            "Select PDF file"
                                        )
                                    )

                                } else {
                                    if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                        pdfIntent.type = "application/pdf"
                                        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                        pdfAadhaarLauncher.launch(
                                            Intent.createChooser(
                                                pdfIntent,
                                                "Select PDF file"
                                            )
                                        )
                                    } else {
                                        showSettingsDialog(this@AddSellerKYCActivity)
                                    }
                                }

                                dialog.dismiss()
                            }

                            override fun onCameraClick(dialog: BottomSheetDialog) {
                                if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                    cameraLauncherAadhaar.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                                } else {
                                    showSettingsDialog(this@AddSellerKYCActivity)
                                }

                                dialog.dismiss()
                            }

                        })
                } catch (e: Exception) {
                    mLog("Select or Capture Exception ${e.message}")
                }

            }

            etUploadSellerGST.setOnClickListener {
                try {
                    CustomDialogs.showBottomSheetDialog(this@AddSellerKYCActivity,
                        object : CustomDialogs.OnBottomSheetClickListener {
                            override fun onImageClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                                    mLog("PERMISSION_GRANTED 13")
                                    selectGSTImageLauncher.launch(gallery)
                                } else {
                                    if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                        val gallery = Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI
                                        )
                                        mLog("setInitialSetup: PERMISSION_GRANTED below 13")
                                        selectGSTImageLauncher.launch(gallery)
                                    } else {
                                        showSettingsDialog(this@AddSellerKYCActivity)
                                    }

                                }

                                dialog.dismiss()
                            }

                            override fun onFileClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                    pdfIntent.type = "application/pdf"
                                    pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                    pdfGSTLauncher.launch(
                                        Intent.createChooser(
                                            pdfIntent,
                                            "Select PDF file"
                                        )
                                    )

                                } else {
                                    if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                        pdfIntent.type = "application/pdf"
                                        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                        pdfGSTLauncher.launch(
                                            Intent.createChooser(
                                                pdfIntent,
                                                "Select PDF file"
                                            )
                                        )
                                    } else {
                                        showSettingsDialog(this@AddSellerKYCActivity)
                                    }
                                }

                                dialog.dismiss()
                            }

                            override fun onCameraClick(dialog: BottomSheetDialog) {
                                if (verifyCameraPermission(this@AddSellerKYCActivity)) {
                                    cameraLauncherGST.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                                } else {
                                    showSettingsDialog(this@AddSellerKYCActivity)
                                }

                                dialog.dismiss()
                            }

                        })
                } catch (e: Exception) {
                    mLog("Select or Capture Exception ${e.message}")
                }

            }

            /* etUploadEnvironmentNo.setOnClickListener {
                 try {
                     CustomDialogs.showBottomSheetDialog(this@AddBuyerKYCActivity,
                         object : CustomDialogs.OnBottomSheetClickListener {
                             override fun onImageClick(dialog: BottomSheetDialog) {
                                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                     val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                                     mLog("PERMISSION_GRANTED 13")
                                     selectEnvironmentImageLauncher.launch(gallery)
                                 } else {
                                     if (verifyCameraPermission(this@AddBuyerKYCActivity)) {
                                         val gallery = Intent(
                                             Intent.ACTION_PICK,
                                             MediaStore.Images.Media.INTERNAL_CONTENT_URI
                                         )
                                         mLog("setInitialSetup: PERMISSION_GRANTED below 13")
                                         selectEnvironmentImageLauncher.launch(gallery)
                                     } else {
                                         showSettingsDialog(this@AddBuyerKYCActivity)
                                     }

                                 }

                                 dialog.dismiss()
                             }

                             override fun onFileClick(dialog: BottomSheetDialog) {
                                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                     val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                     pdfIntent.type = "application/pdf"
                                     pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                     pdfEnvironmentLauncher.launch(
                                         Intent.createChooser(
                                             pdfIntent,
                                             "Select PDF file"
                                         )
                                     )

                                 } else {
                                     if (verifyCameraPermission(this@AddBuyerKYCActivity)) {
                                         val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                         pdfIntent.type = "application/pdf"
                                         pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                         pdfEnvironmentLauncher.launch(
                                             Intent.createChooser(
                                                 pdfIntent,
                                                 "Select PDF file"
                                             )
                                         )
                                     } else {
                                         showSettingsDialog(this@AddBuyerKYCActivity)
                                     }
                                 }

                                 dialog.dismiss()
                             }

                             override fun onCameraClick(dialog: BottomSheetDialog) {
                                 if (verifyCameraPermission(this@AddBuyerKYCActivity)) {
                                     cameraLauncherEnvironment.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                                 } else {
                                     showSettingsDialog(this@AddBuyerKYCActivity)
                                 }

                                 dialog.dismiss()
                             }

                         })
                 } catch (e: Exception) {
                     mLog("Select or Capture Exception ${e.message}")
                 }


                 *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                     val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                     mLog("PERMISSION_GRANTED 13")
                     uploadEnvironmentLaunchContract.launch(gallery)
                 } else {
                     val permit = verifyStoragePermission(this@AddBuyerKYCActivity)
                     if (permit != PackageManager.PERMISSION_GRANTED)
                     else {
                         val gallery = Intent(
                             Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI
                         )
                         Log.d("MyTag", "setInitialSetup: PERMISSION_GRANTED below 13")
                         uploadEnvironmentLaunchContract.launch(gallery)
                     }
                 }*//*
            }
            etUploadCancelChequeNo.setOnClickListener {
                try {
                    CustomDialogs.showBottomSheetDialog(this@AddBuyerKYCActivity,
                        object : CustomDialogs.OnBottomSheetClickListener {
                            override fun onImageClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                                    mLog("PERMISSION_GRANTED 13")
                                    selectCancelChequeImageLauncher.launch(gallery)
                                } else {
                                    if (verifyCameraPermission(this@AddBuyerKYCActivity)) {
                                        val gallery = Intent(
                                            Intent.ACTION_PICK,
                                            MediaStore.Images.Media.INTERNAL_CONTENT_URI
                                        )
                                        mLog("setInitialSetup: PERMISSION_GRANTED below 13")
                                        selectCancelChequeImageLauncher.launch(gallery)
                                    } else {
                                        showSettingsDialog(this@AddBuyerKYCActivity)
                                    }

                                }

                                dialog.dismiss()
                            }

                            override fun onFileClick(dialog: BottomSheetDialog) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                    pdfIntent.type = "application/pdf"
                                    pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                    pdfCancelChequeLauncher.launch(
                                        Intent.createChooser(
                                            pdfIntent,
                                            "Select PDF file"
                                        )
                                    )

                                } else {
                                    if (verifyCameraPermission(this@AddBuyerKYCActivity)) {
                                        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
                                        pdfIntent.type = "application/pdf"
                                        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
                                        pdfCancelChequeLauncher.launch(
                                            Intent.createChooser(
                                                pdfIntent,
                                                "Select PDF file"
                                            )
                                        )
                                    } else {
                                        showSettingsDialog(this@AddBuyerKYCActivity)
                                    }
                                }

                                dialog.dismiss()
                            }

                            override fun onCameraClick(dialog: BottomSheetDialog) {
                                if (verifyCameraPermission(this@AddBuyerKYCActivity)) {
                                    cameraLauncherCancelCheque.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                                } else {
                                    showSettingsDialog(this@AddBuyerKYCActivity)
                                }

                                dialog.dismiss()
                            }

                        })
                } catch (e: Exception) {
                    mLog("Select or Capture Exception ${e.message}")
                }


                *//* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                     val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                     mLog("PERMISSION_GRANTED 13")
                     uploadCancelledChequeLaunchContract.launch(gallery)
                 } else {
                     val permit = verifyStoragePermission(this@AddBuyerKYCActivity)
                     if (permit != PackageManager.PERMISSION_GRANTED)
                     else {
                         val gallery = Intent(
                             Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI
                         )
                         Log.d("MyTag", "setInitialSetup: PERMISSION_GRANTED below 13")
                         uploadCancelledChequeLaunchContract.launch(gallery)
                     }
                 }*//*
            }
*/
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
                        mPref.getSellerDetail()?.let {
                            val addBuyerRes = AddSellerRequest().apply {
                                seller_type = it.seller_type
                                full_name = it.full_name
                                email = it.email
                                phone = it.phone
                                company_name = it.company_name
                                company_email = it.company_email
                                company_phone = it.company_phone
                                profile_image = it.profile_image
                                website = it.website
                                pan_no = etSellerPanNo.text.toString()
                                aadhar_no = etSellerAadhaarNo.text.toString()
                                gst_no = etSellerGSTNo.text.toString()
                                aadhar_file_path = aadhaarImage
                                pan_file_path = panCardImage
                                gst_file_path = gstImage
                            }
                            mPref.addSellerDetail(addBuyerRes)
                        }
                        mLog("panCar $panCardImage")
                        mLog("aadhaar $aadhaarImage")
                        mLog("gst $gstImage")
                        mLog("req ${mPref.getSellerDetail()}")
                        launchActivity(AddSellerBankActivity::class.java)
                    }
                }
            }

        }
    }


    private val selectPANImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val uri = result.data?.data!!
                    Log.d("MyTag", "data $uri")
                    mBind.ivUploadSellerPan.apply {
                        visibility = View.VISIBLE
                        setImageURI(uri)
                    }
                    panCardImage = getRealPathFromURI(uri!!, this@AddSellerKYCActivity).toString()
                }
            } catch (e: Exception) {
                panCardImage = null
                mBind.ivUploadSellerPan.visibility = View.GONE
                mLog("pan selected file error")
            }

        }

    private val pdfPanActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageUri: Uri = result.data?.data!!
                    var file = getRealPathFromURI(imageUri, this@AddSellerKYCActivity)
                    if (checkFileSize(File(file))!! < 2.toDouble()) {
                        mBind.ivUploadSellerPan.visibility = View.VISIBLE
                        panCardImage = file
                        Glide.with(this@AddSellerKYCActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(mBind.ivUploadSellerPan)
                    } else {
                        mToast(getString(R.string.pdf_should_be_less_then_or_equal_2mb))
                    }
                }
            } catch (e: Exception) {
                mLog("Nothing Selected Image")
                mBind.ivUploadSellerPan.visibility = View.GONE
                panCardImage = null
            }

        }

    private val cameraLauncherPan =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val b = data?.extras?.get("data") as Bitmap
                    val path = compressImageFilePath(b, this@AddSellerKYCActivity)

                    if (checkFileSize(File(path))!! < 2.toDouble()) {
                        mBind.ivUploadSellerPan.visibility = View.VISIBLE
                        panCardImage = path
                        mBind.ivUploadSellerPan.setImageBitmap(b)
                        mLog("Image path : $panCardImage")
                    } else {
                        mToast(getString(R.string.image_should_be_less_then_or_equal_2mb))
                    }
                }
            } catch (e: Exception) {
                mLog("Nothing Selected Image")
                panCardImage = null
                mBind.ivUploadSellerPan.visibility = View.GONE

            }
        }


    private val selectAadhaarImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val uri = result.data?.data!!
                    Log.d("MyTag", "data $uri")
                    mBind.ivUploadSellerAadhaar.apply {
                        visibility = View.VISIBLE
                        setImageURI(uri)
                    }
                    aadhaarImage = getRealPathFromURI(uri!!, this@AddSellerKYCActivity).toString()
                }
            } catch (e: Exception) {
                aadhaarImage = null
                mBind.ivUploadSellerAadhaar.visibility = View.GONE
                mLog("pan selected file error")
            }

        }

    private val pdfAadhaarLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageUri: Uri = result.data?.data!!
                    var file = getRealPathFromURI(imageUri, this@AddSellerKYCActivity)
                    if (checkFileSize(File(file))!! < 2.toDouble()) {
                        mBind.ivUploadSellerAadhaar.visibility = View.VISIBLE
                        aadhaarImage = file
                        Glide.with(this@AddSellerKYCActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(mBind.ivUploadSellerAadhaar)
                    } else {
                        mToast(getString(R.string.pdf_should_be_less_then_or_equal_2mb))
                    }
                }
            } catch (e: Exception) {
                mLog("Nothing Selected Image")
                aadhaarImage = null
            }

        }

    private val cameraLauncherAadhaar =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val b = data?.extras?.get("data") as Bitmap
                    val path = compressImageFilePath(b, this@AddSellerKYCActivity)
                    if (checkFileSize(File(path))!! < 2.toDouble()) {
                        aadhaarImage = path
                        mBind.ivUploadSellerAadhaar.apply {
                            visibility = View.VISIBLE
                            setImageBitmap(b)
                        }
                        mLog("Image path : $aadhaarImage")
                    } else {
                        mToast(getString(R.string.image_should_be_less_then_or_equal_2mb))
                    }
                }
            } catch (e: Exception) {
                mLog("Nothing Selected Image")
                aadhaarImage = null
                mBind.ivUploadSellerAadhaar.visibility = View.GONE

            }
        }


    private val selectGSTImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val uri = result.data?.data!!
                    Log.d("MyTag", "data $uri")
                    mBind.ivUploadSellerGstImg.apply {
                        visibility = View.VISIBLE
                        setImageURI(uri)
                    }
                    gstImage = getRealPathFromURI(uri!!, this@AddSellerKYCActivity).toString()
                }
            } catch (e: Exception) {
                gstImage = null
                mBind.ivUploadSellerGstImg.visibility = View.GONE
                mLog("pan selected file error")
            }

        }

    private val pdfGSTLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val imageUri: Uri = result.data?.data!!
                    var file = getRealPathFromURI(imageUri, this@AddSellerKYCActivity)
                    if (checkFileSize(File(file))!! < 2.toDouble()) {
                        mBind.ivUploadSellerGstImg.visibility = View.VISIBLE

                        gstImage = file
                        Glide.with(this@AddSellerKYCActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(mBind.ivUploadSellerGstImg)
                    } else {
                        mToast(getString(R.string.pdf_should_be_less_then_or_equal_2mb))
                    }
                }
            } catch (e: Exception) {
                mLog("Nothing Selected Image")
                mBind.ivUploadSellerGstImg.visibility = View.GONE
                gstImage = null
            }

        }

    private val cameraLauncherGST =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val b = data?.extras?.get("data") as Bitmap
                    val path = compressImageFilePath(b, this@AddSellerKYCActivity)

                    if (checkFileSize(File(path))!! < 2.toDouble()) {
                        mBind.ivUploadSellerGstImg.visibility = View.VISIBLE

                        gstImage = path
                        mBind.ivUploadSellerGstImg.setImageBitmap(b)
                        mLog("Image path : $gstImage")
                    } else {
                        mToast(getString(R.string.image_should_be_less_then_or_equal_2mb))
                    }
                }
            } catch (e: Exception) {
                mLog("Nothing Selected Image")
                mBind.ivUploadSellerGstImg.visibility = View.GONE
                gstImage = null
            }
        }


    /*
        private val selectEnvironmentImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result.resultCode == Activity.RESULT_OK) {
                        // There are no request codes
                        val uri = result.data?.data!!
                        Log.d("MyTag", "data $uri")
                        mBind.ivUploadEnvironmentImg.apply {
                            visibility = View.VISIBLE
                            setImageURI(uri)
                        }
                        environmentImage =
                            getRealPathFromURI(uri!!, this@AddBuyerKYCActivity).toString()
                    }
                } catch (e: Exception) {
                    environmentImage = null
                    mBind.ivUploadEnvironmentImg.visibility = View.GONE
                    mLog("pan selected file error")
                }

            }

        private val pdfEnvironmentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result.resultCode == Activity.RESULT_OK) {
                        val imageUri: Uri = result.data?.data!!
                        var file = getRealPathFromURI(imageUri, this@AddBuyerKYCActivity)
                        if (checkFileSize(File(file))!! < 2.toDouble()) {
                            mBind.ivUploadEnvironmentImg.visibility = View.VISIBLE
                            environmentImage = file
                            Glide.with(this@AddBuyerKYCActivity)
                                .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                                .into(mBind.ivUploadEnvironmentImg)
                        } else {
                            mToast(getString(R.string.pdf_should_be_less_then_or_equal_2mb))
                        }
                    }
                } catch (e: Exception) {
                    mLog("Nothing Selected Image")
                    environmentImage = null
                    mBind.ivUploadEnvironmentImg.visibility = View.GONE
                }

            }

        private val cameraLauncherEnvironment =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result.resultCode == Activity.RESULT_OK) {
                        val data: Intent? = result.data
                        val b = data?.extras?.get("data") as Bitmap
                        val path = compressImageFilePath(b, this@AddBuyerKYCActivity)
                        if (checkFileSize(File(path))!! < 2.toDouble()) {
                            mBind.ivUploadEnvironmentImg.visibility = View.VISIBLE
                            environmentImage = path
                            mBind.ivUploadEnvironmentImg.setImageBitmap(b)
                            mLog("Image path : $gstImage")
                        } else {
                            mToast(getString(R.string.image_should_be_less_then_or_equal_2mb))
                        }
                    }
                } catch (e: Exception) {
                    mLog("Nothing Selected Image")
                    environmentImage = null
                    mBind.ivUploadEnvironmentImg.visibility = View.GONE
                }
            }


        private val selectCancelChequeImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result.resultCode == Activity.RESULT_OK) {
                        // There are no request codes
                        val uri = result.data?.data!!
                        Log.d("MyTag", "data $uri")
                        mBind.ivetUploadCancelChequeNoImg.apply {
                            visibility = View.VISIBLE
                            setImageURI(uri)
                        }
                        cancelledChequeImage =
                            getRealPathFromURI(uri!!, this@AddBuyerKYCActivity).toString()
                    }
                } catch (e: Exception) {
                    cancelledChequeImage = null
                    mBind.ivetUploadCancelChequeNoImg.visibility = View.GONE
                }

            }

        private val pdfCancelChequeLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result.resultCode == Activity.RESULT_OK) {
                        val imageUri: Uri = result.data?.data!!
                        var file = getRealPathFromURI(imageUri, this@AddBuyerKYCActivity)
                        if (checkFileSize(File(file))!! < 2.toDouble()) {
                            mBind.ivetUploadCancelChequeNoImg.visibility = View.VISIBLE
                            cancelledChequeImage = file
                            Glide.with(this@AddBuyerKYCActivity)
                                .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                                .into(mBind.ivetUploadCancelChequeNoImg)
                        } else {
                            mToast(getString(R.string.pdf_should_be_less_then_or_equal_2mb))
                        }
                    }
                } catch (e: Exception) {
                    cancelledChequeImage = null
                    mBind.ivetUploadCancelChequeNoImg.visibility = View.GONE
                }

            }

        private val cameraLauncherCancelCheque =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                try {
                    if (result.resultCode == Activity.RESULT_OK) {
                        val data: Intent? = result.data
                        val b = data?.extras?.get("data") as Bitmap
                        val path = compressImageFilePath(b, this@AddBuyerKYCActivity)

                        if (checkFileSize(File(path))!! < 2.toDouble()) {
                            mBind.ivetUploadCancelChequeNoImg.visibility = View.VISIBLE
                            cancelledChequeImage = path
                            mBind.ivetUploadCancelChequeNoImg.setImageBitmap(b)
                            mLog("Image path : $gstImage")
                        } else {
                            mToast(getString(R.string.image_should_be_less_then_or_equal_2mb))
                        }
                    }
                } catch (e: Exception) {
                    cancelledChequeImage = null
                    mBind.ivetUploadCancelChequeNoImg.visibility = View.GONE
                }
            }
    */


}