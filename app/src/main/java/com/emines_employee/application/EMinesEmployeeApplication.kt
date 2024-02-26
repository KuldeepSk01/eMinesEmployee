package com.emines_employee.application

import android.app.Application
import android.util.Log
import com.emines_employee.koin.appModule
import com.emines_employee.koin.repositoryModule
import com.emines_employee.koin.viewModelModule
import com.emines_employee.network.NetworkConnectionManager
import com.emines_employee.util.setContext
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class EMinesEmployeeApplication:Application(),KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EMinesEmployeeApplication)
            androidLogger()
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }

        if (checkGooglePlayService()){
            Log.d("TAG", "onCreate: google play service is Available...")
        }else{
            Log.d("TAG", "onCreate: google play service is UnAvailable...")
        }


        NetworkConnectionManager(applicationContext)
        setContext(this@EMinesEmployeeApplication)


    }

    private fun checkGooglePlayService(): Boolean {
        val googleAvailability = GoogleApiAvailability.getInstance()
        val result = googleAvailability.isGooglePlayServicesAvailable(this@EMinesEmployeeApplication)
        if (result == ConnectionResult.SUCCESS) {
            return true
        } else if (googleAvailability.isUserResolvableError(result)
        ) {
            /* val dialog :Dialog = googleAvailability.getErrorDialog(this,result,201,object:DialogInterface.OnCancelListener{
                 override fun onCancel(p0: DialogInterface?) {
                     Toast.makeText(this@StayHookApplication, "User cancel the dialog", Toast.LENGTH_SHORT).show()
                     p0?.dismiss()
                 }
             })
             dialog.show()*/
        }
        return false
    }


}