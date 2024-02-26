package com.emines_employee.screen.dashboard.orders

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnItemClickOrderListener
import com.emines_employee.base.BaseFragment
import com.emines_employee.databinding.FragmentOrdersBinding
import com.emines_employee.model.Order
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.screen.dashboard.orders.detail.OrderDetailActivity
import com.emines_employee.util.mLog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class OrdersFragment : BaseFragment(), TabLayout.OnTabSelectedListener, OnItemClickOrderListener {

    private lateinit var ordersBinding: FragmentOrdersBinding
    val list = OrderViewModel.getOrderList()

    private var tabText = "Buying Request"

    override fun getLayoutId() = R.layout.fragment_orders
    override fun onCreateViewInit(binding: ViewDataBinding, view: View) {
        ordersBinding = binding as FragmentOrdersBinding
        ordersBinding.apply {
            toolBarOrder.apply {
                ivToolBarBack.visibility = View.GONE
                tvToolBarTitle.text = String.format("%s %s", "All", getText(R.string.orders_text))
            }
            //setOrdersLise(listBuying)

            // tabLayout1Order.selectTab(tabLayout1Order.getTabAt(0))

            tabLayout2Order.selectTab(tabLayout2Order.getTabAt(0))

            tabLayout2Order.addTab(
                tabLayout2Order.newTab().setText(getString(R.string.buying_request_text))
            )
            tabLayout2Order.addTab(
                tabLayout2Order.newTab().setText(getString(R.string.purchase_order))
            )
            tabLayout2Order.addTab(tabLayout2Order.newTab().setText(getString(R.string.orders)))
            tabLayout1Order.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tex = tab?.text.toString()
                    mLog("Select request type $tex")

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })

            tabLayout2Order.addOnTabSelectedListener(this@OrdersFragment)


            /*
                        tabLayout2Order.addTab(
                            tabLayout2Order.newTab().setText(getString(R.string.completed_text))
                        )
                        tabLayout2Order.addOnTabSelectedListener(this@OrdersFragment)
            */

            //  tabLayout1Order.addOnTabSelectedListener(this@OrdersFragment)


            /*val pendingList =
                list.filter { order -> order.status == getString(R.string.pending_text) }
            Log.d("OrderFragment", "onCreateViewInit: completeList $pendingList")

*/
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tabText = tab?.text.toString()
//        val filterList =
//            list.filter { order -> order.status?.lowercase() == tab?.text.toString().lowercase() }
//        ordersBinding.rvOrder.adapter = OrderAdapter(
//            filterList as MutableList<Order>, requireContext(), this@OrdersFragment
//        )

        when (tab?.text) {
            getString(R.string.buying_request_text) -> {
                //setOrdersLise(listBuying)
            }

            getString(R.string.purchase_order) -> {
              //  setOrdersLise(listPurchase)
            }

            getString(R.string.orders) -> {
                setOrdersLise(list)
            }
        }

        Log.d("OrderFragment", "onCreateViewInit: completeList $list")
        Log.d("OrderFragment", "onTabSelected: item text ${tab?.text}")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onItemOrderClick(order: Order) {
        launchActivity(OrderDetailActivity::class.java)
        //   replaceFragment(R.id.flMainContainer,OrderDetailFragment(),OrderFragment::javaClass.name)
        Log.d("OrderFragment", "onItemOrderClick: $order")
    }

    override fun onClickBuyingRequest(order: RequestOrderResponse) {
        launchActivity(OrderDetailActivity::class.java)
    }

    override fun onClickPurchaseOrder(order: RequestOrderResponse) {
        launchActivity(OrderDetailActivity::class.java)
    }


    private fun setOrdersLise(list: MutableList<Order>) {
        ordersBinding.rvOrder.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            /*
                        when (tabText) {
                            getString(R.string.buying_request_text) -> {
                                adapter = OrderBuyingReqAdapter(
                                    list, requireContext(), this@OrdersFragment
                                )
                                // mToast("Buiying")
                            }

                            getString(R.string.purchase_text) -> {
                                adapter = OrderPOAdapter(
                                    list, requireContext(), this@OrdersFragment
                                )
                                //mToast("Purchase")

                            }

                            getString(R.string.orders) -> {
                                adapter = OrderAdapter(
                                    list as MutableList<Order>, requireContext(), this@OrdersFragment
                                )
                                // mToast("Order")

                            }
                        }
            */
        }


    }

}