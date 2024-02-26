package com.emines_employee.screen.verifyme2

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityVerifyMe2Binding
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import org.koin.core.component.inject


class VerifyMe2Activity : BaseActivity() {
    private lateinit var mBinding: ActivityVerifyMe2Binding
    private val mViewModel: VerifyMe2ViewModel by inject()
    private val userDetail = mPref.getUserDetail()

    override val layoutId: Int
        get() = R.layout.activity_verify_me2

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityVerifyMe2Binding
        checkForUpdate()
        mViewModel.verifyMe2Activity = this@VerifyMe2Activity
        mBinding.mViewModel = mViewModel
        mBinding.apply {
            userDetail?.let {
                Glide.with(this@VerifyMe2Activity).load(it.profilePic).placeholder(R.drawable.profile_img).into(ivVerifyImg)
                tvVerifyProfileTitle.text =
                    String.format("%s %s \n%s", it.name, it.lastName, it.currentCity)
            }

            toolbarVerifyMe2.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.attendance_text)
            }
        }

    }


    private fun checkForUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this@VerifyMe2Activity)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // an activity result launcher registered via registerForActivityResult
                    activityResultLauncher,
                    // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                    // flexible updates.
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                )
            }
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
            // handle callback
            if (result.resultCode != AppCompatActivity.RESULT_OK) {
                Log.d(
                    "App Update",
                    "Update flow failed! Result code: " + result.resultCode
                );
                // If the update is canceled or fails,
                // you can request to start the update again.
            } else {
                Log.d(
                    "App Update",
                    "App Update is available "
                );
            }

        }


}