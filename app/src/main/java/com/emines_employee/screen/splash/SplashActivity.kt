package com.emines_employee.screen.splash

import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivitySplashBinding
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.screen.onboarding.OnBoardingActivity
import com.emines_employee.screen.verifyme2.VerifyMe2Activity
import com.emines_employee.util.Constants
import com.emines_employee.util.getCurrentDate
import com.emines_employee.util.mLog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class SplashActivity : BaseActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private lateinit var mRunnable: Runnable
    private lateinit var mHandler: Handler

    override val layoutId = R.layout.activity_splash

    override fun onCreateInit(binding: ViewDataBinding?) {
        splashBinding = binding as ActivitySplashBinding
        mHandler = Handler(Looper.getMainLooper())
        splashBinding.tvAppVersion.text = String.format("%s %s",getString(R.string.version1_0),"1.0")

        mRunnable = Runnable {
            if (mPref[Constants.PreferenceConstant.IS_LOGIN,0]==1){

              //  launchActivity(VerifyMe2Activity::class.java)

                if (mPref[Constants.PreferenceConstant.IS_CURRENT_DATE,""] == getCurrentDate()){
                    mLog("IS Present true")
                    launchActivity(MainActivity::class.java)
                    finish()
                    mLog("IS Present false")
                }else{
                    launchActivity(VerifyMe2Activity::class.java)
                    finish()
                }

            }else{
                launchActivity(OnBoardingActivity::class.java)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mHandler.postDelayed(mRunnable, 3000)
    }


    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(mRunnable)
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(mRunnable)
    }
}