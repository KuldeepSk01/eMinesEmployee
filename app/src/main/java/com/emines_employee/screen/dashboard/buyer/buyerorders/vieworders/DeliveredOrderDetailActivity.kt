package com.emines_employee.screen.dashboard.buyer.buyerorders.vieworders

import android.app.Dialog
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityBuyingReqDetailBinding
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.checkIsImageExtensions
import com.emines_employee.util.downloadFileFromUrl
import com.emines_employee.util.mToast
import com.emines_employee.util.serializable

class DeliveredOrderDetailActivity : BaseActivity() {
    private lateinit var mBind: ActivityBuyingReqDetailBinding
    private lateinit var requestOrderResponse: RequestOrderResponse

    override val layoutId: Int
        get() = R.layout.activity_buying_req_detail

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityBuyingReqDetailBinding
        requestOrderResponse = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<RequestOrderResponse>(Constants.DefaultConstant.MODEL_DETAIL)!!
        mBind.apply {
            toolBarBReqDetail.apply {
                tvToolBarTitle.text = String.format(
                    "%s %s%s", getString(R.string.order_id_text),
                    getString(R.string.defaultID), requestOrderResponse.id.toString()
                )

                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }

            /*toolBarBReqDetail.apply {
                tvToolBarTitle.text = getString(R.string.enquiry_detail)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }*/


            requestOrderResponse?.let {
                tvBReqId.text = String.format(" %s", it.po_no.toString())
                tvPickupLocation.text = it.destination
                tvDropLocationPickupDetail.text = it.address

                tvAssignedDateBReq.text =
                    String.format("%s %s", getString(R.string.assigned_date), it.assigned_date)
                tvItemPOAssignTransporter.text = String.format(
                    "%s %s",
                    getString(R.string.assigned_to_transporter),
                    it.assigned_to
                )
                tvPickupDate.text = String.format("%s %s", "Picked Up Date :", it.pickup_date)
                tvdeliveredDate.text = String.format("%s %s", "Delivered Date :", it.delivery_date)

                tvBReqStatus.apply {
                    // val date = formatDate(it.delivery_date,"dd-MM-yyyy","dd MMM yyy")
                    this.text = String.format("%s, %s", it.status, " ${it.delivery_date}")
                    this.setTextColor(context.getColor(R.color.order_complete_color))
                    /*


                                        when(it.status) {
                                            context.getString(R.string.assigned)->{
                                                this.text = String.format("%s, %s",it.status," ${it.assigned_date}")
                                                this.setTextColor(context.getColor(R.color.order_confirm_color))
                                            }
                                            context.getString(R.string.new_order)->{
                                                this.text = it.status
                                                this.setTextColor(context.getColor(R.color.order_confirm_color))
                                            }
                                            context.getString(R.string.reached)->{
                                                this. text = String.format("%s, %s",it.status," ${it.reached_date}")
                                                this. setTextColor(context.getColor(R.color.order_complete_color))
                                            }
                                            context.getString(R.string.accepted)->{
                                                this.text = String.format("%s, %s",it.status," ${it.accepte_date}")
                                                this.setTextColor(context.getColor(R.color.order_complete_color))
                                            }
                                            context.getString(R.string.delivered)->{
                                                this. text = String.format("%s, %s",it.status," ${it.delivery_date}")
                                                this.setTextColor(context.getColor(R.color.order_complete_color))
                                            }
                                            context.getString(R.string.picked_up)->{
                                                this. text = String.format("%s, %s",it.status," ${it.pickup_date}")
                                                this.setTextColor(context.getColor(R.color.order_picked_color))
                                            }
                                            context.getString(R.string.denied)->{
                                                this.text = String.format("%s, %s",it.status," ${it.denied_date}")
                                                this.setTextColor(context.getColor(R.color.red_alert_color))
                                            }
                                            context.getString(R.string.in_process)->{
                                                this.setTextColor(context.getColor(R.color.order_placed_color))
                                            }
                                            else->{
                                                this.text = String.format("%s",it.status)
                                                this.setTextColor(context.getColor(R.color.order_confirm_color))

                                            }
                                        }
                    */
                }
                rlDeliveryLayout.visibility = View.VISIBLE

                /*
                                if (!it.weight_receipt.isNullOrEmpty()){
                                    rlInProcessLayout.visibility = View.GONE
                                }else{
                                    rlInProcessLayout.visibility = View.GONE
                                }

                                if (!it.delivered_weight_receipt.isNullOrEmpty()){
                                    rlDeliveryLayout.visibility = View.GONE
                                }else{
                                    rlDeliveryLayout.visibility = View.GONE
                                }*/

                tvGoods1.text = it.one_requested_product
                tvPQ1.text = it.one_purchased_quantity
                tvUnit1.text = it.one_unit_of_quantity
                tvRate1.text = it.one_unit_rate
                tvTAmount1.text = it.one_total_unit_price_excl_gst
                tvGstAmount1.text = it.one_total_unit_price_incl_gst
                tvGst1.text = it.one_product_gst

                tvGoods2.text = it.two_requested_product
                tvPQ2.text = it.two_purchased_quantity
                tvUnit2.text = it.two_unit_of_quantity
                tvRate2.text = it.two_unit_rate
                tvTAmount2.text = it.two_total_unit_price_excl_gst
                tvGstAmount2.text = it.two_total_unit_price_incl_gst
                tvGst2.text = it.two_product_gst


                tvGoods3.text = it.three_requested_product
                tvPQ3.text = it.three_purchased_quantity
                tvUnit3.text = it.three_unit_of_quantity
                tvRate3.text = it.three_unit_rate
                tvTAmount3.text = it.three_total_unit_price_excl_gst
                tvGstAmount3.text = it.three_total_unit_price_incl_gst
                tvGst3.text = it.three_product_gst


                tvGoods4.text = it.four_requested_product
                tvPQ4.text = it.four_purchased_quantity
                tvUnit4.text = it.four_unit_of_quantity
                tvRate4.text = it.four_unit_rate
                tvTAmount4.text = it.four_total_unit_price_excl_gst
                tvGstAmount4.text = it.four_total_unit_price_incl_gst
                tvGst4.text = it.four_product_gst


                if (it.two_requested_product?.isNullOrEmpty()!!) {
                    llcGoods2.visibility = View.GONE
                } else {
                    llcGoods2.visibility = View.VISIBLE
                }

                if (it.three_requested_product.isNullOrEmpty()) {
                    llcGoods3.visibility = View.GONE
                } else {
                    llcGoods3.visibility = View.VISIBLE
                }

                if (it.four_requested_product.isNullOrEmpty()) {
                    llcGoods4.visibility = View.GONE
                } else {
                    llcGoods4.visibility = View.VISIBLE
                }




                tvWeightOne.text = it.pickup_weight
                tvWeight2.text = it.delivery_weight
                tvEWayBill.text = it.bill_number
                tvGRN.text = it.grn_number

                ivViewWReceiptDeliveredDetail.apply {
                    if (checkIsImageExtensions(it.weight_receipt)) {
                        Glide.with(this@DeliveredOrderDetailActivity).load(it.weight_receipt)
                            .into(this)
                    } else {
                        Glide.with(this@DeliveredOrderDetailActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(this)
                    }

                    setOnClickListener { click ->
                        if (it.weight_receipt.isNullOrEmpty()) {
                            mToast(getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }

                        if (checkIsImageExtensions(it.weight_receipt)) {
                            CustomDialogs.showImageDialog(
                                this@DeliveredOrderDetailActivity,
                                it.weight_receipt,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            CustomDialogs.showWebViewDialog(
                                this@DeliveredOrderDetailActivity,
                                it.weight_receipt,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        }

                    }
                }
                ivViewEWayBillDeliveredDetail.apply {
                    if (checkIsImageExtensions(it.eway_bill)) {
                        Glide.with(this@DeliveredOrderDetailActivity).load(it.eway_bill).into(this)
                    } else {
                        Glide.with(this@DeliveredOrderDetailActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(this)
                    }

                    setOnClickListener { click ->
                        if (it.eway_bill.isNullOrEmpty()) {
                            mToast(getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }

                        if (checkIsImageExtensions(it.eway_bill)) {
                            CustomDialogs.showImageDialog(
                                this@DeliveredOrderDetailActivity,
                                it.eway_bill,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            CustomDialogs.showWebViewDialog(
                                this@DeliveredOrderDetailActivity,
                                it.eway_bill,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        }

                    }
                }
                ivViewWReceipt2DeliveredDetail.apply {
                    if (checkIsImageExtensions(it.delivered_weight_receipt)) {
                        Glide.with(this@DeliveredOrderDetailActivity)
                            .load(it.delivered_weight_receipt).into(this)
                    } else {
                        Glide.with(this@DeliveredOrderDetailActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(this)
                    }

                    setOnClickListener { click ->
                        if (it.delivered_weight_receipt.isNullOrEmpty()) {
                            mToast(getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }

                        if (checkIsImageExtensions(it.delivered_weight_receipt)) {
                            CustomDialogs.showImageDialog(
                                this@DeliveredOrderDetailActivity,
                                it.delivered_weight_receipt,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            CustomDialogs.showWebViewDialog(
                                this@DeliveredOrderDetailActivity,
                                it.delivered_weight_receipt,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        }

                    }
                }
                ivViewGRNDeliveredDetail.apply {
                    if (checkIsImageExtensions(it.grn_file)) {
                        Glide.with(this@DeliveredOrderDetailActivity).load(it.grn_file).into(this)
                    } else {
                        Glide.with(this@DeliveredOrderDetailActivity)
                            .load("https://blog.idrsolutions.com/app/uploads/2020/10/pdf-1.png")
                            .into(this)
                    }

                    setOnClickListener { click ->
                        if (it.grn_file.isNullOrEmpty()) {
                            mToast(getString(R.string.file_not_uploaded))
                            return@setOnClickListener
                        }

                        if (checkIsImageExtensions(it.grn_file)) {
                            CustomDialogs.showImageDialog(
                                this@DeliveredOrderDetailActivity,
                                it.grn_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        } else {
                            CustomDialogs.showWebViewDialog(
                                this@DeliveredOrderDetailActivity,
                                it.grn_file,
                                object : CustomDialogs.OnShowImageDialogListener {
                                    override fun onClickDownload(dialog: Dialog, url: String) {
                                        downloadFileFromUrl(url)
                                        // dialog.dismiss()
                                    }
                                }).show()
                        }

                    }
                }


            }


        }


    }


}