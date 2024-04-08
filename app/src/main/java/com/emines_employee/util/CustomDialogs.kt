package com.emines_employee

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.emines_employee.adapter.PopupItemPickAdapter
import com.emines_employee.databinding.DailogProgressLayoutBinding
import com.emines_employee.databinding.DialogDatePickerBinding
import com.emines_employee.databinding.DialogLoginSuccessBinding
import com.emines_employee.databinding.DialogOrderConfirmSuccessBinding
import com.emines_employee.databinding.DialogPopupListBinding
import com.emines_employee.databinding.DialogSelectFileBinding
import com.emines_employee.databinding.DialogTimePickerBinding
import com.emines_employee.databinding.ViewImageLayoutBinding
import com.emines_employee.databinding.ViewWebviewDialogLayoutBinding
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.util.getDateFormat
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object CustomDialogs {

    interface CustomDialogsListener {
        fun onComplete(d: Dialog)
    }

    interface DatePickerDialogListener {
        fun onPicker(d: Dialog, str: String)
    }

    fun successProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val dB = DataBindingUtil.inflate<DailogProgressLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.dailog_progress_layout,
            null,
            false
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

    fun showCustomSuccessDialog(
        context: Context,
        btnName: String,
        title: String,
        listener: CustomDialogsListener
    ): Dialog {

        /*val dialog = BottomSheetDialog(
            context, android.R.style.Theme_Translucent // it means u can see your screen in background from when dialog is pop up
        )*/

        val dialog = Dialog(context)
        val dB = DataBindingUtil.inflate<DialogOrderConfirmSuccessBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_order_confirm_success,
            null,
            false
        )
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dB.tvOrderSuccess.text = title
        dB.tvOrderConfirmBtn.apply {
            text = btnName
            setOnClickListener {
                listener.onComplete(dialog)
            }
        }
        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showDatePickerDialog(
        context: Context,
        listener: DatePickerDialogListener
    ): Dialog {

        var date = ""

        val dialog = Dialog(context)
        val dB = DataBindingUtil.inflate<DialogDatePickerBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_date_picker,
            null,
            false
        )
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dB.apply {
            datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                date = getDateFormat(dayOfMonth, monthOfYear, year)
            }
            tvOkBtn.setOnClickListener {
                listener.onPicker(dialog, date)
            }

        }

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dB.root)
        dialog.create()
        dialog.show()
        return dialog
    }

    fun showTimePickerDialog(
        context: Context,
        listener: DatePickerDialogListener
    ): Dialog {

        var date = ""

        val dialog = Dialog(context)
        val dB = DataBindingUtil.inflate<DialogTimePickerBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_time_picker,
            null,
            false
        )
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dB.apply {
            timePicker.setIs24HourView(true)
            timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
              val c=  Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY,hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
                val sdf = SimpleDateFormat("HH:mm a",Locale.getDefault())
                date = sdf.format(c.time)
               // listener.onPicker(dialog,date)
            }

            tvOkBtn.setOnClickListener {
                listener.onPicker(dialog, date)
            }

        }

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dB.root)
        dialog.create()
        dialog.show()
        return dialog
    }

    fun showLoginSuccessDialog(
        context: Context,
        listener: CustomDialogsListener
    ): Dialog {

        /*val dialog = BottomSheetDialog(
            context, android.R.style.Theme_Translucent // it means u can see your screen in background from when dialog is pop up
        )*/

        val dialog = Dialog(context)
        val dB = DataBindingUtil.inflate<DialogLoginSuccessBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_login_success,
            null,
            false
        )
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dB.tvSuccessContinueBtn.apply {
            setOnClickListener {
                listener.onComplete(dialog)
            }
        }


        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }


    fun showSuccessDialog(
        context: Context,
        btnName: String,
        title: String,
        subTitle: String,
        listener: CustomDialogsListener
    ): Dialog {


        val dialog = Dialog(context)
        val dB = DataBindingUtil.inflate<DialogLoginSuccessBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_login_success,
            null,
            false
        )
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dB.apply {
            tv1DialogLogin.text = title
            tv2DialogLogin.text = subTitle
            tvSuccessContinueBtn.apply {
                text = btnName
                setOnClickListener {
                    listener.onComplete(dialog)
                }
            }
        }

        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

    fun showSelectProfileBottomSheetDialog(context: Context, listener: OnBottomSheetClickListener) {
        val dialog = BottomSheetDialog(context, R.style.DialogStyle)
        val bottomSheet = DataBindingUtil.inflate<DialogSelectFileBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_select_file,
            null,
            false
        )
        bottomSheet.apply {
            closeButton.setOnClickListener { dialog.dismiss() }
            rlDocument.visibility = View.GONE
            imageCamera.setOnClickListener {
                listener.onCameraClick(dialog)
            }
            imageFile.setOnClickListener {
                listener.onImageClick(dialog)
            }
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(bottomSheet.root)
        dialog.show()
    }

    fun showBottomSheetDialog(context: Context, listener: OnBottomSheetClickListener) {
        val dialog = BottomSheetDialog(context, R.style.DialogStyle)
        val bottomSheet = DataBindingUtil.inflate<DialogSelectFileBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_select_file,
            null,
            false
        )
        bottomSheet.closeButton.setOnClickListener { dialog.dismiss() }
        bottomSheet.imageCamera.setOnClickListener {
            listener.onCameraClick(dialog)
        }
        bottomSheet.imageFile.setOnClickListener {
            listener.onImageClick(dialog)
        }
        bottomSheet.pdfFile.setOnClickListener {
            listener.onFileClick(dialog)
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(bottomSheet.root)
        dialog.show()
    }


    interface OnBottomSheetClickListener {
        fun onImageClick(dialog: BottomSheetDialog)
        fun onFileClick(dialog: BottomSheetDialog)
        fun onCameraClick(dialog: BottomSheetDialog)
    }



    fun showImageDialog(
        context: Context,
        imageURl: String,
        listener: OnShowImageDialogListener
    ): Dialog {

        val dialog = Dialog(context,android.R.style.Theme_Material)
        val dB = DataBindingUtil.inflate<ViewImageLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.view_image_layout,
            null,
            false
        )
        dialog.window?.setGravity(Gravity.CENTER)
        // dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dB.apply {
            Glide.with(context).load(imageURl).into(ivImage)
            tvDownloadImg.apply {
                setOnClickListener {
                    listener.onClickDownload(dialog,imageURl)
                }
            }
            ivCrossBtn.apply {
                setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }

    interface OnShowImageDialogListener{
        fun onClickDownload(dialog: Dialog,url:String)
    }

    fun showWebViewDialog(
        context: Context,
        fileUrl: String,
        listener: OnShowImageDialogListener
    ): Dialog {
        val dialog = Dialog(context,android.R.style.Theme_Material)
        val dB = DataBindingUtil.inflate<ViewWebviewDialogLayoutBinding>(
            LayoutInflater.from(context),
            R.layout.view_webview_dialog_layout,
            null,
            false
        )
        dialog.window?.setGravity(Gravity.CENTER)
        //  dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dB.apply {
            ivCrossBtn.apply {
                setOnClickListener {
                    dialog.dismiss()
                }
            }
            webView.apply {
                settings.javaScriptEnabled = true
                settings.builtInZoomControls = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                loadUrl("http://docs.google.com/viewer?url=$fileUrl")
            }
            tvDownloadImg.apply {
                setOnClickListener {
                    listener.onClickDownload(dialog,fileUrl)
                }
            }
        }
        dialog.setCancelable(false)
        dialog.setContentView(dB.root)
        dialog.create()
        return dialog
    }





    interface OnPopupItemPickerListener{
        fun onItemPick(model:BuyersResponse)
    }
    fun popupDialog(context: Context,list:MutableList<BuyersResponse>,listener:OnPopupItemPickerListener,title:String){
        val alertDialog = Dialog(context)
        val b = DataBindingUtil.inflate<DialogPopupListBinding>(
            LayoutInflater.from(context),
            R.layout.dialog_popup_list,
            null,
            false
        )
        b.tvAllBuyerSeller.text = title
        b.rvSelectTime.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PopupItemPickAdapter(list,
                context,
                object : PopupItemPickAdapter.OnScheduledTimePickerListener {
                    override fun onTimePicker(model: BuyersResponse) {
                        Log.d("TAG", "onTimePicker: time $model")
                        //vTimePickerScheduleVisit.text = model.name
                        listener.onItemPick(model)
                        alertDialog.dismiss()
                    }
                })
        }
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(b.root)
        alertDialog.create()
        alertDialog.show()
    }

}