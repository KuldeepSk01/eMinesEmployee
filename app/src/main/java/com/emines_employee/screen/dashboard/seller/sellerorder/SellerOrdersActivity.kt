package com.emines_employee.screen.dashboard.seller.sellerorder

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.OrderBuyingReqAdapter
import com.emines_employee.adapter.OrderPOAdapter
import com.emines_employee.adapter.listener.OnClickBuyerOrderListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.ActivitySellerOrdersBinding
import com.emines_employee.model.request.GetSellerOrderRequest
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.buyerorders.vieworders.BuyingReqDetailActivity
import com.emines_employee.screen.dashboard.buyer.buyerorders.vieworders.DeliveredOrderDetailActivity
import com.emines_employee.screen.dashboard.buyer.buyerorders.vieworders.InProcessDetailActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.serializable
import com.google.android.material.tabs.TabLayout
import org.koin.core.component.inject

class SellerOrdersActivity : BaseActivity(),
    TabLayout.OnTabSelectedListener, OnClickBuyerOrderListener {

    private lateinit var mBind: ActivitySellerOrdersBinding
    private val mViewModel: SellerOrderViewModel by inject()
    var orderList = mutableListOf<RequestOrderResponse>()

    private var tabText = "Selling Request"
    private var buyerID = -1


    override val layoutId: Int
        get() = R.layout.activity_seller_orders

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivitySellerOrdersBinding
        val sellerResponse = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(Constants.DefaultConstant.MODEL_DETAIL)
        buyerID = sellerResponse!!.id

        mBind.apply {
            toolBarBuyerOrder.apply {
                tvToolBarTitle.text = String.format(
                    "%s %s", sellerResponse?.company_name, "(${getString(R.string.seller)})"
                )
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

            }
            tabLayout2BuyerOrder.selectTab(tabLayout2BuyerOrder.getTabAt(0))
            tabLayout2BuyerOrder.addOnTabSelectedListener(this@SellerOrdersActivity)

        }
        hitGetOrdersApi(getString(R.string.selling_request))

    }

    override fun onClickBuyingRequest(order: RequestOrderResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, order)
        launchActivity(BuyingReqDetailActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)
    }


    override fun onClickInProcessOrder(order: RequestOrderResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, order)
        launchActivity(InProcessDetailActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)

    }

    override fun onClickDeliveredOrder(order: RequestOrderResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, order)
        launchActivity(
            DeliveredOrderDetailActivity::class.java,
            Constants.DefaultConstant.BUNDLE_KEY,
            b
        )
    }

    /* override fun onClickPurchaseOrder(order: RequestOrderResponse) {
         //mBind.tabLayout2BuyerOrder.selectTab(mBind.tabLayout2BuyerOrder.getTabAt(2))
         //setOrdersLise(list)
         val b = Bundle()
         b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, order)
        launchActivity(BuyingReqDetailActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)

     }*/

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabText = tab?.text.toString()

        when (tab?.text) {
            getString(R.string.selling_request) -> {
                hitGetOrdersApi(getString(R.string.selling_request))
            }

            getString(R.string.purchase_order) -> {
                hitGetOrdersApi(getString(R.string.po_uploaded))
            }

            getString(R.string.orders) -> {
                hitGetOrdersApi(getString(R.string.orders))
            }

            getString(R.string.pick_up) -> {
                hitGetOrdersApi(getString(R.string.pick_up))
            }

            getString(R.string.in_process) -> {
                hitGetOrdersApi(getString(R.string.in_process))
            }

            getString(R.string.delivered) -> {
                hitGetOrdersApi(getString(R.string.delivered))
            }
        }

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }


    private fun hitGetOrdersApi(status: String) {
        val req = GetSellerOrderRequest().apply {
            sellerId = buyerID
            userId = mPref.getUserDetail()!!.id
            orderStatus = status
        }

        mViewModel.hitSellerOrderListListApi(req)
        mViewModel.getSellerOrderListResponse().observe(this, orderListObserver)
    }


    private val orderListObserver =
        Observer<ApiResponse<CollectionBaseResponse<RequestOrderResponse>>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    orderList = it.data?.data as MutableList<RequestOrderResponse>
                    if (orderList.isEmpty()) {
                        mBind.rlNoDataAvailable.visibility = View.VISIBLE
                        mBind.rvBuyerOrder.visibility = View.GONE
                    } else {
                        mBind.rlNoDataAvailable.visibility = View.GONE
                        mBind.rvBuyerOrder.visibility = View.VISIBLE
                    }
                    setCategoryList(orderList)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                }
            }
        }

    private fun setCategoryList(orderList: MutableList<RequestOrderResponse>) {
        mLog("Order List $orderList")
        mBind.rvBuyerOrder.apply {
            layoutManager =
                LinearLayoutManager(this@SellerOrdersActivity, LinearLayoutManager.VERTICAL, false)
            when (tabText) {
                getString(R.string.selling_request) -> {
                    adapter = OrderBuyingReqAdapter(
                        orderList, this@SellerOrdersActivity, this@SellerOrdersActivity
                    )
                }

                /* getString(R.string.purchase_order) -> {
                     adapter = OrderPOAdapter(
                         orderList, this@BuyerOrderActivity, this@BuyerOrderActivity
                     )
                 }
 */
                /*  getString(R.string.orders) -> {
                      adapter = OrderPOAdapter(
                          orderList, this@BuyerOrderActivity, this@BuyerOrderActivity
                      )
                  }*/

                else -> {
                    adapter = OrderPOAdapter(
                        orderList, this@SellerOrdersActivity, this@SellerOrdersActivity
                    )
                }
            }
        }

    }

}