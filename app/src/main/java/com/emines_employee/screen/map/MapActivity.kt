package com.emines_employee.screen.map

import android.util.Log
import androidx.databinding.ViewDataBinding
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityMapBinding
import com.emines_employee.permissions.MyPermissions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MapActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var mapBinding: ActivityMapBinding
    private lateinit var mGoogleMap: GoogleMap
    override val layoutId: Int
        get() = R.layout.activity_map

    override fun onCreateInit(binding: ViewDataBinding?) {
        mapBinding = binding as ActivityMapBinding
        if (MyPermissions.isLocationEnable) {
            launchActivity(MainActivity::class.java)
            finish()
        } else {
            Dexter.withContext(this)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        MyPermissions.isLocationEnable = true
                        Log.d("LocationPermission", "onPermissionGranted: Granted..")
                        launchActivity(MainActivity::class.java)
                        finish()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Log.d("LocationPermission", "onPermissionGranted: Denied..")
                        MyPermissions.isLocationEnable = false
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        MyPermissions.isLocationEnable = false
                        Log.d("LocationPermission", "onPermissionGranted: RationalShould")
                    }

                }).check()
        }

        val supportMapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this@MapActivity)
    }

    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap = p0


    }
}