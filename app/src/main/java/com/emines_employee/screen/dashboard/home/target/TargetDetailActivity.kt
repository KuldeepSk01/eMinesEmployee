package com.emines_employee.screen.dashboard.home.target

import androidx.databinding.ViewDataBinding
import com.emines_employee.R
import com.emines_employee.adapter.listener.OnItemClickOrderListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityTargetDetailBinding
import com.emines_employee.model.Order
import com.emines_employee.model.Targets
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.screen.dashboard.orders.OrderViewModel
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.serializable
import com.google.android.material.tabs.TabLayout

class TargetDetailActivity : BaseActivity(), TabLayout.OnTabSelectedListener,
    OnItemClickOrderListener {
    private lateinit var targetDetailBinding: ActivityTargetDetailBinding
    val list = OrderViewModel.getOrderList()


    override val layoutId: Int
        get() = R.layout.activity_target_detail

    override fun onCreateInit(binding: ViewDataBinding?) {
        targetDetailBinding = binding as ActivityTargetDetailBinding
        val m = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
            ?.serializable<Targets>(Constants.DefaultConstant.MODEL_DETAIL)!!

        targetDetailBinding.apply {
            tvQ1ItemTarget.text = String.format("%s%s", "Q${m.id}", "-${m.quarterDate}")
            tvQ1ItemTargetPercent.text = String.format("%d%s", m.quarterPercentage, "%")
            tvItemTargetValue.text = String.format("%s %s", m.quarterTarget, "Rs")
            tvItemTargetAchievedValue.text = String.format("%d %s", m.quarterAchievedTarget, "Rs")
            tvItemTargetDate.text = m.quarterMonths


            /*tabLayout1TargetDetail.selectTab(tabLayout1TargetDetail.getTabAt(0))
            tabLayout2TargetDetail.selectTab(tabLayout2TargetDetail.getTabAt(0))


            tabLayout2TargetDetail.addOnTabSelectedListener(this@TargetDetailActivity)
            rvTargetDetail.itemAnimator = DefaultItemAnimator()
            rvTargetDetail.layoutManager =
                LinearLayoutManager(this@TargetDetailActivity, LinearLayoutManager.VERTICAL, false)

            val pendingList =
                list.filter { order -> order.status == getString(R.string.pending_text) }
            Log.d("OrderFragment", "onCreateViewInit: completeList $pendingList")


            rvTargetDetail.adapter = OrderAdapter(
                pendingList as MutableList<Order>,
                this@TargetDetailActivity,
                this@TargetDetailActivity
            )*/

        }

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        /* val filterList =
             list.filter { order -> order.status?.lowercase() == tab?.text.toString().lowercase() }
         targetDetailBinding.rvTargetDetail.adapter = OrderAdapter(
             filterList as MutableList<Order>, this@TargetDetailActivity, this@TargetDetailActivity
         )
         mLog("onTabSelected${tab?.text}")
         mLog("completeList $filterList")*/
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onItemOrderClick(order: Order) {
        mLog("clicked order $order")

    }

    override fun onClickBuyingRequest(order: RequestOrderResponse) {

    }

    override fun onClickPurchaseOrder(order: RequestOrderResponse) {

    }
}