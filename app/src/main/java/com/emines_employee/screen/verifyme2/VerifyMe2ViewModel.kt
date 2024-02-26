package com.emines_employee.screen.verifyme2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.emines_employee.base.BaseResponse
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.MarkPresentRepo
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.buildAlertMessageNoGps
import com.emines_employee.util.getCurrentDate
import com.emines_employee.util.isGpsProvider
import com.emines_employee.util.isLocationEnable
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.setLocationPermission
import com.emines_employee.util.showSettingsDialog
import com.google.android.gms.location.FusedLocationProviderClient
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.Locale

class VerifyMe2ViewModel(private val repo: VerifyProfileRepo) : BaseViewModel(),LocationListener {

    lateinit var verifyMe2Activity: VerifyMe2Activity
    private val LOCATION_UPDATE_TIME = 1000L   //1 Milli Second
    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager
    private var onClickPresentOne = true
    private lateinit var address: String
    private val markPresentResponse = MutableLiveData<ApiResponse<SuccessMsgResponse>>()

    @RequiresApi(Build.VERSION_CODES.Q)
    fun onPresentClickBtn() {
        if (!isGpsProvider(verifyMe2Activity)){
            buildAlertMessageNoGps(verifyMe2Activity)
            return
        }
        if (onClickPresentOne){
            Dexter.withContext(verifyMe2Activity).withPermissions(
                arrayListOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.areAllPermissionsGranted()!!) {
                        mLog("Permission grated")
                        onClickPresentOne = false
                       // setLocationPermission(true)
                        gpsInit()
                    } else {
                       // setLocationPermission(false)
                        mLog("Permission not grated")
                        onClickPresentOne = true
                        showSettingsDialog(verifyMe2Activity)
                       // mToast(Constants.DefaultConstant.LOCATION_PERMISSION_ERROR)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                    mLog("Permission should be granted...")
                }
            }).withErrorListener {
                mLog("Permission Error ${it.name}")
            }.check()
            //onClickPresentOne = false
        }else{
           mToast("please wait while fetching your location.")
        }

    }

    private fun hitMarkPresentApi() {
        val req = MarkPresentRepo().apply {
            userId = mPref.getUserDetail()?.id!!
            isHalfDay = 0
            currentLocation = address
        }
        repo.executeMarkPresentApi(req, markPresentResponse)
        markPresentResponse.observe(verifyMe2Activity, markPresentObserver)
    }



    private fun gpsInit() {
        locationManager = verifyMe2Activity.getSystemService<LocationManager>()!!

        if (isGpsProvider(verifyMe2Activity)){
            if (ActivityCompat.checkSelfPermission(
                    verifyMe2Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    verifyMe2Activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_UPDATE_TIME,
                0.5F,
                this@VerifyMe2ViewModel
            )
        }

        /*val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocationByGps!=null){
            lastKnownLocationByGps.let {
                mLog("lastLocation location $it")
                currentLocation = it
                getAddressFromLatLng(it.latitude, it.longitude)
                mLog("Address lat lng ${it.latitude} ${it.longitude} ")

            }
        }else{
            if (hasGps) {
                mLog("update Location location ")
                if (ActivityCompat.checkSelfPermission(
                        verifyMe2Activity.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        verifyMe2Activity.applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_TIME,
                    0.1F,
                    this@VerifyMe2ViewModel
                )
            }
        }
*/
    }

    private fun getAddressFromLatLng(lat: Double, lng: Double) {
        try {
            val geocoder = Geocoder(verifyMe2Activity, Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                lat,
                lng,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            address =
                addresses!![0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            mLog("Address : MyCurrent address $address")
            hitMarkPresentApi()
        }catch (e:Exception){
            mLog("Address : exception address ${e.message}")
            mToast("Location Failed !")
            onClickPresentOne = true
        }



    }

    private val markPresentObserver: Observer<ApiResponse<SuccessMsgResponse>> by lazy {
        Observer {

            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    verifyMe2Activity.showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    verifyMe2Activity.hideProgress()
                    val cDate = getCurrentDate()
                    mPref.put(Constants.PreferenceConstant.IS_CURRENT_DATE, cDate)

                    verifyMe2Activity.launchActivity(MainActivity::class.java)
                    verifyMe2Activity.finish()
                }

                ApiResponse.Status.ERROR -> {
                    verifyMe2Activity.hideProgress()
                    mToast(it.error?.message.toString())
                }
            }

        }
    }

    fun onClickApplyLeaveMeBtn() {
        verifyMe2Activity.launchActivity(ApplyLeaveActivity::class.java)
        verifyMe2Activity.finish()
    }

    override fun onLocationChanged(location: Location) {
        try {
            currentLocation = location
            mLog("updated location $location")
            getAddressFromLatLng(location.latitude, location.longitude)
            locationManager.removeUpdates(this@VerifyMe2ViewModel)

        }catch (e:Exception){
            mLog("geoLocation Address Exception: ${e.message}")
        }


    }

    val profileResponse = MutableLiveData<ApiResponse<BaseResponse<UserResponse>>>()
    fun hitProfileApi(userId: Int) {
        repo.executeProfileApi(userId, profileResponse)
    }
}