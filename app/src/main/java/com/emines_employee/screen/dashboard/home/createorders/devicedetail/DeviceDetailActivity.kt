package com.emines_employee.screen.dashboard.home.createorders.devicedetail

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.adapter.UploadImagesAdapter
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityDeviceDetailBinding
import com.emines_employee.model.CreateBuyerOrderRequest
import com.emines_employee.model.UploadImages
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.permissions.MyPermissions.getCameraAccessPermission
import com.emines_employee.permissions.MyPermissions.isCameraAccessEnable
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.compressImageFilePath
import com.emines_employee.util.getRealPathFromURI
import com.emines_employee.util.mLog
import com.emines_employee.util.serializable

class DeviceDetailActivity : BaseActivity() {
    private lateinit var deviceDetailBinding: ActivityDeviceDetailBinding
    private var imagesCount = 0
    private var uploadImageList: MutableList<UploadImages> = mutableListOf()
    private var imagesPathList: MutableList<String> = mutableListOf()

    override val layoutId: Int
        get() = R.layout.activity_device_detail

    override fun onCreateInit(binding: ViewDataBinding?) {
        deviceDetailBinding = binding as ActivityDeviceDetailBinding
        val orderRequest = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<CreateBuyerOrderRequest>(
                Constants.DefaultConstant.MODEL_DETAIL
            )
        deviceDetailBinding.apply {
            getNotifyImages()
            toolbarUploadImg.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                    text = getString(R.string.upload_stock_images)
                }
            }

            tvUploadStockImagesBtn.setOnClickListener {
                if (isCameraAccessEnable) {
                    if (imagesCount == 4) {
                        Toast.makeText(
                            this@DeviceDetailActivity,
                            "Max limit is 4.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        imagesCount++
                        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        resultLauncher.launch(i)
                    }
                } else {
                    getCameraAccessPermission(this@DeviceDetailActivity)
                }

            }

            bottomButtons.apply {
                tvFirstBtn.text = getString(R.string.back_cap)
                tvSecondBtn.text = getString(R.string.confirm)
                tvFirstBtn.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvSecondBtn.setOnClickListener {
                    //orderRequest?.uploadImages = imagesPathList
                    // launchActivity(DeviceQuestionsActivity::cl
                    // ass.java) "Your order has been request successfully."
                    mLog("Your order request ${orderRequest.toString()}")
                   /* CustomDialogs.showCustomSuccessDialog(this@DeviceDetailActivity,
                        getString(R.string.confirmed_text),object : CustomDialogs.CustomDialogsListener{
                            override fun onComplete(d: Dialog) {
                                d.dismiss()
                                launchActivity(MainActivity::class.java)
                            }
                        }).show()*/


                }
            }
        }


    }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data: Intent? = result.data
                    val b = data?.extras?.get("data") as Bitmap
                    val imagesUrls = compressImageFilePath(b,this)
                    mLog("Camera Image uri ${compressImageFilePath(b,this)}")
                    uploadImageList.add(UploadImages(b, "image $imagesCount"))
                    imagesPathList.add(imagesUrls)
                    getNotifyImages()
                }
            }catch (e:Exception){
                mLog("Capture Image Exception ${e.message}")
            }
        }

    private fun getNotifyImages() {
        deviceDetailBinding.apply {
            rvUploadImages.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager = GridLayoutManager(this@DeviceDetailActivity, 2)
                adapter = UploadImagesAdapter(uploadImageList, this@DeviceDetailActivity)
            }
        }
    }

}