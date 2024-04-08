package com.emines_employee.screen.verifyme2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityVerifyMe2Binding
import com.emines_employee.util.buildAlertMessageNoGps
import com.emines_employee.util.isGpsProvider
import com.emines_employee.util.mLog
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import org.koin.core.component.inject
import java.io.IOException
import java.util.Locale


class VerifyMe2Activity : BaseActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityVerifyMe2Binding
    private val mViewModel: VerifyMe2ViewModel by inject()
    private val userDetail = mPref.getUserDetail()

    private var googleMap: GoogleMap? = null
    private var location: Location? = null
    var address: String? = null
    lateinit var locationManager: LocationManager

    override val layoutId: Int
        get() = R.layout.activity_verify_me2

    override fun onResume() {
        super.onResume()
        mViewModel.onClickPresentOne = true
    }

    @SuppressLint("MissingPermission")
    override fun onCreateInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityVerifyMe2Binding
        checkForUpdate()
        mViewModel.verifyMe2Activity = this@VerifyMe2Activity
        mBinding.mViewModel = mViewModel

        getAddressFromMap()

        mBinding.apply {
            userDetail?.let {
                Glide.with(this@VerifyMe2Activity).load(it.profilePic)
                    .placeholder(R.drawable.profile_img).into(ivVerifyImg)
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
    private  val mLocationListener = LocationListener {
        location = it
        if (googleMap != null) {
            val mark = LatLng(location!!.latitude, location!!.longitude)
            googleMap!!.addMarker(
                MarkerOptions()
                    .position(mark)
                    .title("My Location")
            )
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(mark, 15f), 1000, null)
        }
        if (location != null) {
            address = getAddress(this, location!!.latitude, location!!.longitude)
            if (!address.isNullOrEmpty())
                mLog("mAddress  Current $address")
        }
    }


    private fun getAddressFromMap() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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
        if (!isGpsProvider(this@VerifyMe2Activity)) {
            buildAlertMessageNoGps(this@VerifyMe2Activity)
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            100F,
            mLocationListener
        )
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

    private fun getAddress(context: Context, lat: Double, lng: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)!!
            val obj: Address = addresses[0]
            val add: String = obj.getAddressLine(0)
            add
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            null
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
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
        googleMap!!.isMyLocationEnabled = true
        googleMap!!.uiSettings.isMyLocationButtonEnabled = true

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location1: Location? ->
            location = location1
            if (location != null) {
                //binding.locLayout.visibility = View.VISIBLE
                address = getAddress(this, location!!.latitude, location!!.longitude)
                if (!address.isNullOrEmpty())
                    mLog("mAddress  updated $address")
                //binding.locNameTxt.text = address

                val mark = LatLng(location!!.latitude, location!!.longitude)
                googleMap!!.addMarker(
                    MarkerOptions()
                        .position(mark)
                        .title("My Location")
                )
                googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(mark, 15f), 1000, null)
            }
        }
    }


    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(mLocationListener)
    }

}