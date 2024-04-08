package com.emines_employee.screen.dashboard.buyer.buyerorders

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
import com.emines_employee.databinding.ActivityBuyerOrderBinding
import com.emines_employee.model.request.GetBuyerOrderRequest
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.buyerorders.vieworders.BuyingReqDetailActivity
import com.emines_employee.screen.dashboard.buyer.buyerorders.vieworders.DeliveredOrderDetailActivity
import com.emines_employee.screen.dashboard.buyer.buyerorders.vieworders.InProcessDetailActivity
import com.emines_employee.screen.dashboard.actvitylog.LOGActivityViewModel
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.serializable
import com.google.android.material.tabs.TabLayout
import org.koin.core.component.inject

class BuyerOrderActivity : BaseActivity(),
    TabLayout.OnTabSelectedListener, OnClickBuyerOrderListener {

    private lateinit var mBind: ActivityBuyerOrderBinding
    private val mViewModel: BuyerOrderViewModel by inject()

    /* val listPurchase = OrderViewModel.getOrderPurchaseList()
     val listBuying = OrderViewModel.getOrderBuyingReqList()
 */
    var orderList = mutableListOf<RequestOrderResponse>()

    private var tabText = "Buying Request"
    private var buyerID = -1


    override val layoutId: Int
        get() = R.layout.activity_buyer_order

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityBuyerOrderBinding
        val buyerResponse = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<BuyersResponse>(Constants.DefaultConstant.MODEL_DETAIL)
        buyerID = buyerResponse!!.id
        hitGetOrdersApi(getString(R.string.buying_request_text))

        mBind.apply {
            toolBarBuyerOrder.apply {
                tvToolBarTitle.text = String.format(
                    "%s %s", buyerResponse?.company_name, "(${getString(R.string.buyer)})"
                )
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

            }
            //setOrdersLise(listBuying)
            // tabLayout1Order.selectTab(tabLayout1Order.getTabAt(0))
            tabLayout2BuyerOrder.selectTab(tabLayout2BuyerOrder.getTabAt(0))

            /*
             tabLayout2BuyerOrder.addTab(tabLayout2BuyerOrder.newTab().setText("${getString(R.string.orders)}"))*/
            //tabLayout2BuyerOrder.getTabAt(2)?.view?.isClickable=false

            tabLayout2BuyerOrder.addOnTabSelectedListener(this@BuyerOrderActivity)

        }

    }

    /*
        private fun setOrdersLise(list: MutableList<Order>) {
            mBind.rvBuyerOrder.apply {
                layoutManager =
                    LinearLayoutManager(this@BuyerOrderActivity, LinearLayoutManager.VERTICAL, false)
                when (tabText) {
                    getString(R.string.buying_request_text) -> {
                        adapter = OrderBuyingReqAdapter(
                            list, this@BuyerOrderActivity, this@BuyerOrderActivity
                        )
                        // mToast("Buiying")
                    }
                    getString(R.string.purchase_text) -> {
                        adapter = OrderPOAdapter(
                            list, this@BuyerOrderActivity, this@BuyerOrderActivity
                        )
                        //mToast("Purchase")
                    }
                    getString(R.string.orders) -> {
                        adapter = OrderAdapter(
                            list, this@BuyerOrderActivity, this@BuyerOrderActivity
                        )
                        // mToast("Order")
                    }
                }
            }
        }
    */

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

            getString(R.string.buying_request_text) -> {
                hitGetOrdersApi(getString(R.string.buying_request_text))
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
        val req = GetBuyerOrderRequest().apply {
            buyerId = buyerID
            userId = mPref.getUserDetail()!!.id
            orderStatus = status
        }

        mViewModel.hitBuyerOrderListListApi(req)
        mViewModel.getBuyerOrderListResponse().observe(this, orderListObserver)
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
                LinearLayoutManager(this@BuyerOrderActivity, LinearLayoutManager.VERTICAL, false)
            when (tabText) {
                getString(R.string.buying_request_text) -> {
                    adapter = OrderBuyingReqAdapter(
                        orderList, this@BuyerOrderActivity, this@BuyerOrderActivity
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
                        orderList, this@BuyerOrderActivity, this@BuyerOrderActivity
                    )
                }
            }
        }

    }

}