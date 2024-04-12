package com.emines_employee.util

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.emines_employee.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


private var isConnect = false
private var isGpEnable = false


var IsValidBuyerSellerField = false
private lateinit var context: Context

fun isLocationEnable() = isLocationEnable
private var isLocationEnable = false

fun setLocationPermission(isEnableLocationEnable: Boolean) {
    isLocationEnable = isEnableLocationEnable
}

fun isGpsProvider(context: Context): Boolean {
    val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    if (!manager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!) {
        return false
        // buildAlertMessageNoGps(context);
    } else {
        return true
    }
}

fun buildAlertMessageNoGps(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
        .setCancelable(false)
        .setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, id -> context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
        .setNegativeButton("No",
            DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
    val alert: AlertDialog = builder.create()
    alert.show()
}

fun setContext(c: Context) {
    context = c
}


fun isConnectionAvailable() = isConnect
fun setConnection(isAvailable: Boolean) {
    isConnect = isAvailable
}


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
        true
    } else {
        Toast.makeText(context, context.getString(R.string.oops_no_internet_available),Toast.LENGTH_LONG).show()
        false
    }
}
fun mToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun mLog(msg: String) {
    Log.d("EminesEmployee", msg)
    println(msg)

}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key, T::class.java
    )

    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}


fun View.showKeyboard() {
    requestFocusFromTouch()
    this.postDelayed({
        val im = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(this, InputMethodManager.HIDE_NOT_ALWAYS)
    }, 2000)
}

fun View.hideKeyboard() {
    val im = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

@Throws(ParseException::class)
fun formatDate(date: String, initDateFormat: String, endDateFormat: String): String {
    return if (date.isNotEmpty()) {
        val initDate = SimpleDateFormat(initDateFormat, Locale.getDefault()).parse(date)
        val formatter = SimpleDateFormat(endDateFormat, Locale.getDefault())
        formatter.format(initDate)
    } else {
        ""
    }
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val currentDate = Calendar.getInstance().time
    return sdf.format(currentDate)
}

fun getDateFormat(day: Int, month: Int, year: Int): String {
    //  val sdf = SimpleDateFormat("EEE dd MMM yyyy")
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val mTime: Calendar = Calendar.getInstance()
    mTime.set(Calendar.DAY_OF_MONTH, day)
    mTime.set(Calendar.MONTH, month)
    mTime.set(Calendar.YEAR, year)
    return sdf.format(mTime.time)
}


fun removeAllFragmentsFromFragment(fragment: Fragment) {
    val fm = fragment.requireActivity().supportFragmentManager
    for (i in 0 until fm.backStackEntryCount) {
        fm.popBackStack()
    }
    // onBackPress()
}


fun getRealPathFromURI(uri: Uri, activity: Activity): String? {
    val returnCursor: Cursor = activity.applicationContext.contentResolver.query(
        uri, null, null, null, null
    )!!
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    val file: File = File(activity.applicationContext.filesDir, name)
    try {
        val inputStream: InputStream =
            activity.applicationContext.contentResolver.openInputStream(uri)!!
        val outputStream = FileOutputStream(file)
        var read = 0
        val maxBufferSize = 1 * 1024 * 1024
        val bytesAvailable = inputStream.available()
        val bufferSize = Math.min(bytesAvailable, maxBufferSize)
        val buffers = ByteArray(bufferSize)
        while (inputStream.read(buffers).also { read = it } != -1) {
            outputStream.write(buffers, 0, read)
        }
        inputStream.close()
        outputStream.close()
        Log.e("File Path", "Path " + file.absolutePath)
    } catch (e: java.lang.Exception) {
        Log.e("Exception", e.message!!)
    }
    return file.absolutePath
}


interface OnDropDownListener {
    fun onDropDownClick(item: String)
}

fun dropDownPopup(
    context: Context, isBelow: View, menuLayout: Int, listener: OnDropDownListener
): PopupMenu {
    val popup = PopupMenu(context, isBelow)
    popup.menuInflater.inflate(menuLayout, popup.menu)
    popup.setOnMenuItemClickListener { item ->
        listener.onDropDownClick(item.title.toString())
        true
    }
    return popup
}


fun getCalling(context: Context, mobile: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$mobile")
    context.startActivity(intent)
}

fun verifyStoragePermission(activity: Activity): Int {
    // Check if we have write permission
    val permission = ActivityCompat.checkSelfPermission(
        activity, Manifest.permission.READ_EXTERNAL_STORAGE
    )
    if (permission != PackageManager.PERMISSION_GRANTED) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            Constants.RequestCodes.IMAGE_PICK_CODE
        )
    }
    return permission
}


fun requestLocationPermission(activity: Activity) {
    Dexter.withContext(activity).withPermissions(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
            // check if all permissions are granted
            if (report.areAllPermissionsGranted()) {
                //Toast.makeText(this, "Permissi", Toast.LENGTH_SHORT).show()
            } else {
                showSettingsDialog(activity)
            }

            // check for permanent denial of any permission
            if (report.isAnyPermissionPermanentlyDenied) {
                // show alert dialog navigating to Settings
                // showSettingsDialog(activity)
            }
        }

        override fun onPermissionRationaleShouldBeShown(
            permissions: List<PermissionRequest>, token: PermissionToken
        ) {
            token.continuePermissionRequest()
        }
    }).withErrorListener {
        mToast("Error occurred!")
    }.onSameThread().check()
}


fun requestStoragePermission(activity: Activity) {
    Dexter.withContext(activity).withPermissions(
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
    ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
            // check if all permissions are granted
            if (report.areAllPermissionsGranted()) {
                //Toast.makeText(this, "Permissi", Toast.LENGTH_SHORT).show()
            }
            // check for permanent denial of any permission
            if (report.isAnyPermissionPermanentlyDenied) {
                // show alert dialog navigating to Settings
                // showSettingsDialog(activity)
            }
        }

        override fun onPermissionRationaleShouldBeShown(
            permissions: List<PermissionRequest>, token: PermissionToken
        ) {
            token.continuePermissionRequest()
        }
    }).withErrorListener {
        mToast("Error occurred!")
    }.onSameThread().check()
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun requestStoragePermissionAbove32(activity: Activity) {
    Dexter.withContext(activity).withPermissions(
        Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA
    ).withListener(object : MultiplePermissionsListener {
        override fun onPermissionsChecked(report: MultiplePermissionsReport) {
            // check if all permissions are granted
            if (report.areAllPermissionsGranted()) {
                //Toast.makeText(this, "Permissi", Toast.LENGTH_SHORT).show()
            }
            // check for permanent denial of any permission
            if (report.isAnyPermissionPermanentlyDenied) {
                // show alert dialog navigating to Settings
                //showSettingsDialog()
            }
        }

        override fun onPermissionRationaleShouldBeShown(
            permissions: List<PermissionRequest>, token: PermissionToken
        ) {
            token.continuePermissionRequest()
        }
    }).withErrorListener {
        mToast("Error occurred!")
    }.onSameThread().check()
}

fun showSettingsDialog(activity: Activity) {
    val builder = android.app.AlertDialog.Builder(activity)
    builder.setTitle("Need Permissions")
    builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
    builder.setPositiveButton(
        "GOTO SETTINGS"
    ) { dialog, which ->
        dialog.cancel()
        openSettings(activity)
    }
    builder.setNegativeButton(
        "Cancel"
    ) { dialog, which -> dialog.cancel() }
    builder.show()
}

// navigating user to app settings
private fun openSettings(activity: Activity) {
    val intent =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) //this is open location setting
    intent.data = Uri.parse("package:" + activity.packageName)
    activity.startActivityForResult(intent, 101)
}

fun compressImageFilePath(bm: Bitmap, context: Context): String {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val file = File(context.filesDir, timeStamp + ".png")
    if (file.exists()) {
        file.delete()
    }
    try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(file)
        bm.compress(Bitmap.CompressFormat.JPEG, 50, fos)

    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            assert(fos != null)
            fos!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return file.path
}

fun verifyCameraPermission(activity: Activity): Boolean {
    // Check if we have write permission
    return if (ActivityCompat.checkSelfPermission(
            activity, Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        true
    } else {
        false
    }
}


fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(
        inContext.contentResolver, inImage,
        "Title", null
    )
    return Uri.parse(path)
}


//this is used to calculate size of file
fun checkFileSize(file: File): Long? {
    val fileSizeInBytes = file.length()
    val fileSizeInKB = fileSizeInBytes / 1024
    return fileSizeInKB / 1024
}


fun downloadFileFromUrl(url: String) {
    try {
        val downloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val file = File(url)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val uri1 = Uri.parse(url)
        var request = DownloadManager.Request(uri1)
        val storagePath = Environment.DIRECTORY_DOWNLOADS
        request.setDestinationInExternalPublicDir(
            storagePath,
            "$timeStamp${file.name}"
        )
        request.setTitle(file.name)
        request.setDescription(context.getString(R.string.downloading))
        downloadManager.enqueue(request)

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun checkIsImageExtensions(url: String): Boolean {
    return if (url.contains(".png") || url.contains(".jpg") || url.contains(
            ".jpeg"
        )
    ) {
        true
    } else {
        false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDate(str: String) {
    //  val dateString = "2024-02-20T07:41:07.000000Z"
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
    val instant = Instant.from(formatter.parse(str))
    val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    val dateOnly = dateTime.toLocalDate().toString()
    println(dateOnly)
}


