package com.emines_employee.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

object Constants {
    class RequestCodes{
        companion object
        {
            const val CAMERA_CODE = 100
            const val IMAGE_PICK_CODE = 101
        }
    }

    class UrlsEndPoint{
        companion object{
            const val login="employeelogin"
            const val verifyOtp="employeeverifyotp"
            const val addEmployeeAttendance="add_employee_attendance"
            const val addEmployeeLeave="add_employee_leave"
            const val attendanceSheet="attendance_sheet"
            const val employeeProfile="employee_profile"
            const val employeeTarget="employee_target"

            const val buyerLists="buyerLists"
            const val state_list="state_list"
            const val saveBuyer="saveBuyer"
            const val buyerAddresses="buyerAddresses"
            const val addBuyerAddress="addBuyerAddress"

            const val getCategory="getCategory"
            const val buyerOrderRequest="buyer-order-request"
            const val requestBuyerOrderList="buyerOrdersLists"
            const val employeeDashboard="employee_dashboard"


            const val sellerLists="sellerLists"
            const val saveSeller="saveSeller"
           /* const val buyerAddresses="buyerAddresses"
            const val addBuyerAddress="addBuyerAddress"*/

        }
    }

    class NetworkConstant{
        companion object{
            const val API_SUCCESS = 200
            const val API_AUTH_ERROR = 401
            const val API_TIMEOUT = 60000L
            const val NO_INTERNET_AVAILABLE = "Oops! No Internet"
            const val CONNECTION_LOST = "Oops! Connection lost! "
            const val BASE_URL = "https://emines.co/api/"
        }
    }

    class PreferenceConstant{
        companion object{
            const val PREFERENCE_NAME="EMINES_EMPLOYEE"
            const val IS_LOGIN = "IS_LOGIN"
            const val PRESENT = "1"
            const val IS_EMAIL_OR_MOBILE_INVALID = "IS_EMAIL_OR_MOBILE_INVALID"
            const val IS_EMAIL_OR_MOBILE_VALID = "IS_EMAIL_OR_MOBILE_INVALID"
            const val IS_PRESENT = "IS_PRESENT"
            const val IS_CURRENT_DATE = "IS_CURRENT_DATE"
            const val TOKEN="TOKEN"
            const val AUTHORIZATION="authorization"
            const val USER_DETAIL = "USER_DETAIL"
            const val ADD_BUYER_DETAIL = "ADD_BUYER_DETAIL"
            const val EMPLOYEE = "EMPLOYEE"
            const val IS_ADD_BUYER_INVALID = "IS_ADD_BUYER_INVALID"

        }
    }

    class DefaultConstant{
        companion object{
            const val BUNDLE_KEY = "BUNDLE_KEY"
            const val STRING_KEY = "STRING_KEY"
            const val MODEL_DETAIL="MODEL_DETAIL"
            const val OTP_DETAIL="OTP_DETAIL"
            const val LOCATION_PERMISSION_ERROR="Please enable location permission"
        }
    }


}