package com.emines_employee.screen.dashboard.buyer.createrequest.address

import android.app.Dialog
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.CustomDialogs
import com.emines_employee.R
import com.emines_employee.adapter.BuyerAddressAdapter
import com.emines_employee.adapter.listener.AdapterBuyerAddressListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.databinding.ActivityBuyerAddressSelctionBinding
import com.emines_employee.model.CreateBuyerOrderRequest
import com.emines_employee.model.response.BuyerAddressResponse
import com.emines_employee.model.response.BuyerOrderRequest
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.model.response.Orders
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.screen.dashboard.buyer.BuyersViewModel
import com.emines_employee.screen.dashboard.buyer.createrequest.selectcategory.CategoryViewModel
import com.emines_employee.screen.dashboard.buyer.viewbuyer.addressinfo.EditAddressActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.serializable
import org.koin.core.component.inject

class AddressListActivity : BaseActivity(), AdapterBuyerAddressListener {
    private lateinit var mBind: ActivityBuyerAddressSelctionBinding
    private val mViewModel: BuyersViewModel by inject()
    private var buyerAddress: BuyerAddressResponse? = null
    private val categoryVM: CategoryViewModel by inject()
    private var ordersList = mutableListOf<Orders>()
    private var buyerAddressList = mutableListOf<BuyerAddressResponse>()
    private lateinit var buyerResponse: BuyersResponse


    override val layoutId: Int
        get() = R.layout.activity_buyer_address_selction

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityBuyerAddressSelctionBinding
        val buyerOrderRequestDerail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<CreateBuyerOrderRequest>(
                Constants.DefaultConstant.MODEL_DETAIL
            )
        buyerResponse = buyerOrderRequestDerail!!.buyer!!

        buyerOrderRequestDerail.goodsList.forEach {
            ordersList.add(Orders().apply {
                productId = it.id
                purchesedQty = it.purchaseQuantity!!.toInt()
                unitOfQuantity = it.unitOfQuantity
                unitRate = it.rate!!.toInt()
            })
        }

        mBind.apply {
            toolbarBSA.tvToolBarTitle2.apply {
                setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                text = getString(R.string.select_address)
            }

            tvAddMoreSelectAddressBtn.setOnClickListener {
                val b = Bundle()
                b.putSerializable(
                    Constants.DefaultConstant.MODEL_DETAIL,
                    buyerOrderRequestDerail.buyer
                )
                launchActivity(
                    AddAddressActivity::class.java,
                    Constants.DefaultConstant.BUNDLE_KEY,
                    b
                )
            }


            bottomButtons.apply {
                tvFirstBtn.apply {
                    text = getString(R.string.back_cap)
                    setOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
                tvSecondBtn.apply {
                    text = getString(R.string.next_cap)
                    setOnClickListener {
                        mLog("Order req $ordersList ")

                        if (buyerAddress != null) {
                            val request = BuyerOrderRequest().apply {
                                empId = mPref.getUserDetail()?.id!!
                                buyerId = buyerAddress!!.buyer_id
                                deliveryAddressId = buyerAddress!!.id
                                orders = ordersList
                            }
                            categoryVM.hitBuyerOrderRequestApi(request)
                            categoryVM.getBuyerOrderRequestResponse()
                                .observe(this@AddressListActivity, buyerOrderRequestObserver)
                        } else {
                            mToast("Please select address !")
                        }
                    }
                }
            }

        }

    }

    private val addressResponseObserver: Observer<ApiResponse<CollectionBaseResponse<BuyerAddressResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    buyerAddressList.clear()
                    buyerAddressList = it.data?.data as MutableList<BuyerAddressResponse>
                    mLog("Buyers  $buyerAddressList")
                    setBuyersAddressList(buyerAddressList)

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun setBuyersAddressList(buyerAddressList: MutableList<BuyerAddressResponse>) {
        mBind.rvSelectAddress.apply {
            layoutManager = LinearLayoutManager(
                this@AddressListActivity, LinearLayoutManager.VERTICAL, false
            )
            adapter = BuyerAddressAdapter(
                buyerAddressList, this@AddressListActivity, this@AddressListActivity
            )
        }


    }

    private val buyerOrderRequestObserver = Observer<ApiResponse<SuccessMsgResponse>> {
        when (it.status) {
            ApiResponse.Status.LOADING -> {
                showProgress()
            }

            ApiResponse.Status.SUCCESS -> {
                hideProgress()
                CustomDialogs.showCustomSuccessDialog(this@AddressListActivity,
                    getString(R.string.confirmed_text),
                    "Your order has been request successfully.",
                    object : CustomDialogs.CustomDialogsListener {
                        override fun onComplete(d: Dialog) {
                            d.dismiss()
                            launchActivity(MainActivity::class.java)
                        }
                    }).show()

            }

            ApiResponse.Status.ERROR -> {
                hideProgress()
                mToast(it.error?.message.toString())
            }

        }

    }

    override fun onAddressSelect(address: BuyerAddressResponse) {
        buyerAddress = address
    }

    override fun onEditAddress(address: BuyerAddressResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, address)
        launchActivity(EditAddressActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)
    }


    override fun onResume() {
        mViewModel.hitBuyerAddressListApi(buyerResponse.id)
        mViewModel.getBuyerAddressListResponse()
            .observe(this@AddressListActivity, addressResponseObserver)
        super.onResume()
    }

}
