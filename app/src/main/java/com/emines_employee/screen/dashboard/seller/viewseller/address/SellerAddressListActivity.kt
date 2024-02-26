package com.emines_employee.screen.dashboard.seller.viewseller.address

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.BuyerAddressAdapter
import com.emines_employee.adapter.listener.AdapterBuyerAddressListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.ActivityAddressInfoListBinding
import com.emines_employee.model.response.BuyerAddressResponse
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.BuyersViewModel
import com.emines_employee.screen.dashboard.buyer.createrequest.address.AddAddressActivity
import com.emines_employee.screen.dashboard.buyer.viewbuyer.kycinfo.ViewBuyerKYCActivity
import com.emines_employee.screen.dashboard.seller.viewseller.kyc.ViewSellerKYCActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.serializable
import org.koin.core.component.inject

class SellerAddressListActivity : BaseActivity(), AdapterBuyerAddressListener {
    private lateinit var mBind: ActivityAddressInfoListBinding
    private val mViewModel: BuyersViewModel by inject()
    private var buyerAddressList = mutableListOf<BuyerAddressResponse>()
    private lateinit var buyerAddress: BuyerAddressResponse
    private lateinit var buyerDetail: BuyersResponse
    override val layoutId = R.layout.activity_address_info_list

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAddressInfoListBinding
        buyerDetail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(Constants.DefaultConstant.MODEL_DETAIL)!!
        mBind.apply {
            toolbarBSA.tvToolBarTitle2.apply {
                setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                text = getString(R.string.select_address)
            }
            if (buyerDetail.isEditBuyer) {
                tvAddMoreSelectAddressBtn.setOnClickListener {
                    val b = Bundle()
                    b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, buyerDetail)
                    launchActivity(
                        AddAddressActivity::class.java,
                        Constants.DefaultConstant.BUNDLE_KEY,
                        b
                    )
                }
            } else {
                tvAddMoreSelectAddressBtn.visibility = View.GONE
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
                        val b = Bundle()
                        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, buyerDetail)
                        launchActivity(
                            ViewSellerKYCActivity::class.java,
                            Constants.DefaultConstant.BUNDLE_KEY,
                            b
                        )
                    }
                }
            }

        }
    }

    override fun onResume() {
        mViewModel.hitBuyerAddressListApi(buyerDetail.id)
        mViewModel.getBuyerAddressListResponse()
            .observe(this@SellerAddressListActivity, addressResponseObserver)

        super.onResume()
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
        if (buyerAddressList.isEmpty()) {
            mBind.tvNoAddressFound.visibility = View.VISIBLE
            mBind.rvSelectAddress.visibility = View.GONE
        } else {
            mBind.tvNoAddressFound.visibility = View.GONE
            mBind.rvSelectAddress.visibility = View.VISIBLE
            mBind.rvSelectAddress.apply {
                layoutManager = LinearLayoutManager(
                    this@SellerAddressListActivity, LinearLayoutManager.VERTICAL, false
                )
                adapter = BuyerAddressAdapter(
                    buyerAddressList, this@SellerAddressListActivity, this@SellerAddressListActivity
                )
            }
        }


    }

    override fun onAddressSelect(address: BuyerAddressResponse) {
        buyerAddress = address
    }

    override fun onEditAddress(address: BuyerAddressResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, address)
        launchActivity(SellerEditAddressActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)
    }

}