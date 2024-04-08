package com.emines_employee.screen.dashboard.buyer.createrequest.address

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.CustomTextPickerAdapter
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.databinding.ActivityAddAddressBinding
import com.emines_employee.databinding.DailogCustomTextListBinding
import com.emines_employee.model.request.BuyerAddressRequest
import com.emines_employee.model.response.BuyerAddressStateResponse
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.viewbuyer.addressinfo.BuyersAddressViewModel
import com.emines_employee.util.Constants
import com.emines_employee.util.OnDropDownListener
import com.emines_employee.util.dropDownPopup
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.serializable
import org.koin.core.component.inject

class AddAddressActivity : BaseActivity() {

    private val buyerVm: BuyersAddressViewModel by inject()
    private lateinit var mBind: ActivityAddAddressBinding
    override val layoutId: Int
        get() = R.layout.activity_add_address

    private var stateList = mutableListOf<BuyerAddressStateResponse>()
    private var stateId: Int = -1
    private var countryId: Int = 101

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAddAddressBinding

        val buyerDetail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(
                Constants.DefaultConstant.MODEL_DETAIL
            )

        mBind.apply {
            mLog("state id $stateId country id $countryId")

            toolbarBBA.tvToolBarTitle2.text = getString(R.string.add_address)

            etAInfoType.setOnClickListener {
                dropDownPopup(
                    this@AddAddressActivity,
                    it,
                    R.menu.menu_address_type,
                    object : OnDropDownListener {
                        override fun onDropDownClick(item: String) {
                            etAInfoType.text = item
                        }
                    }).show()
            }
            etAInfoTypeState.setOnClickListener {
                if (stateList.isEmpty()) {
                    //buyerVm.hitStateListApi(buyerDetail!!.id)
                    buyerVm.hitStateListApi()
                    buyerVm.getStateListResponse()
                        .observe(this@AddAddressActivity, stateListObserver)
                } else {
                    mLog("second Call state $stateList")
                    popupDialog(stateList)
                }
            }


            bottomButtons.apply {
                tvFirstBtn.visibility = View.GONE
                tvSecondBtn.apply {
                    text = getString(R.string.save_cap)

                    setOnClickListener {
                        if (etAInfoTypeState.text.toString().isEmpty()) {
                            mToast("Please select state!")
                        } else {
                            val req = BuyerAddressRequest().apply {
                                buyerId = buyerDetail!!.id
                                addressType = etAInfoType.text.toString()
                                city = etAInfoTypeCity.text.toString()
                                state = stateId.toString()
                                country = countryId.toString() //default country code
                                area = etAInfoTypeArea.text.toString()
                                pincode = etAInfoTypePinCode.text.toString()
                                address = etAInfoTypeAddress.text.toString()
                            }

                            mLog("Save address Req ${req.toString()}")

                            buyerVm.hitAddBuyerAddressApi(req)
                            buyerVm.getAddBuyerAddressResponse()
                                .observe(this@AddAddressActivity, saveAddressObserver)
                        }
                    }

                }
            }
        }

    }


    private val saveAddressObserver: Observer<ApiResponse<SuccessMsgResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mToast(it.data?.message.toString())
                    finish()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }


    private val stateListObserver: Observer<ApiResponse<CollectionBaseResponse<BuyerAddressStateResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    stateList.clear()
                    stateList = it.data?.data as MutableList<BuyerAddressStateResponse>
                    mLog("Fist Call state $stateList")
                    popupDialog(stateList)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun popupDialog(list: List<BuyerAddressStateResponse>) {
        val alertDialog = Dialog(this@AddAddressActivity)
        val b = DataBindingUtil.inflate<DailogCustomTextListBinding>(
            LayoutInflater.from(this@AddAddressActivity),
            R.layout.dailog_custom_text_list,
            null,
            false
        )
        b.rvCustomList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CustomTextPickerAdapter(list as MutableList<BuyerAddressStateResponse>,
                this@AddAddressActivity, object : CustomTextPickerAdapter.OnStatePickerListener {
                    override fun onStatePicker(model: BuyerAddressStateResponse) {
                        mBind.etAInfoTypeState.text = model.statename
                        mLog("state pick ${model.toString()}")
                        stateId = model.id
                        countryId = model.country_id
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