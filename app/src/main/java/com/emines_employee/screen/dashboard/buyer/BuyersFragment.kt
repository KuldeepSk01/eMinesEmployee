package com.emines_employee.screen.dashboard.buyer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.BuyerSellerAdapter
import com.emines_employee.adapter.listener.OnCreateBuyerSellerListener
import com.emines_employee.base.BaseFragment
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.FragmentBuyerBinding
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.addbuyer.contactinfo.AddBuyerContactActivity
import com.emines_employee.screen.dashboard.buyer.buyerorders.BuyerOrderActivity
import com.emines_employee.screen.dashboard.buyer.viewbuyer.contactinfo.ViewBuyerContactActivity
import com.emines_employee.screen.dashboard.buyer.createrequest.selectcategory.SelectCategoryActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.IsValidBuyerSellerField
import com.emines_employee.util.getCalling
import com.emines_employee.util.isConnectionAvailable
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import org.koin.core.component.inject

class BuyersFragment : BaseFragment(), OnCreateBuyerSellerListener {

    private lateinit var mBind: FragmentBuyerBinding
    private val mViewModel: BuyersViewModel by inject()
    private var buyerList = mutableListOf<BuyersResponse>()
    private var mContext:Context? = null

    override fun getLayoutId() = R.layout.fragment_buyer
    override fun onCreateViewInit(binding: ViewDataBinding, view: View) {
        mBind = binding as FragmentBuyerBinding
        showTab()
        mBind.apply {
            toolbarBuyers.apply {
                ivToolBarBack.visibility = View.GONE
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                    text = getString(R.string.buyer_list)
                }

            }

            tvAddBuyerBtn.setOnClickListener {
                IsValidBuyerSellerField = false
                // mPref.put(Constants.PreferenceConstant.IS_ADD_BUYER_INVALID,Constants.PreferenceConstant.IS_EMAIL_OR_MOBILE_VALID)
                mPref.addBuyerDetail(AddBuyerRequest())
                launchActivity(
                    AddBuyerContactActivity::class.java
                )
            }
            if (isConnectionAvailable()) {
                mViewModel.hitBuyerListApi()
                mViewModel.getBuyersListResponse().observe(requireActivity(), buyerListResponseObserver)
            } else {
                mToast(getString(R.string.oops_no_internet_available))
            }
        }
    }

    private fun setBuyersList(buyerList: MutableList<BuyersResponse>) {
        mBind.rvBuyer.apply {
            layoutManager =
                LinearLayoutManager(mContext!!.applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter = BuyerSellerAdapter(buyerList, mContext!!.applicationContext, this@BuyersFragment,true)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }
    private val buyerListResponseObserver: Observer<ApiResponse<CollectionBaseResponse<BuyersResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    buyerList.clear()
                    buyerList = it.data?.data as MutableList<BuyersResponse>
                    mLog("Buyers  ${buyerList.toString()}")
                    setBuyersList(buyerList)
                    mBind.tvBuyerAndOrder.text = String.format("%s","Manage Buyer (${buyerList.size}) & Orders")
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    override fun onUpdate(model: BuyersResponse) {
        // mToast("working on it")
      /*  model.isEditBuyer = true
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(ViewBuyerContactActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)
      */
        mLog("Edit Model $model")

    }

    override fun onCreateClick(model: BuyersResponse) {
        // mToast("working on it")
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(SelectCategoryActivity::class.java,Constants.DefaultConstant.BUNDLE_KEY, b)

    }

    override fun onViewClick(model: BuyersResponse) {
        model.isEditBuyer = false

        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(ViewBuyerContactActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)

    }

    override fun onMapClick(model: BuyersResponse) {
        mToast("working on it")
        //Toast.makeText(requireContext(), "working on it", Toast.LENGTH_SHORT).show()
    }

    override fun onCallClick(model: BuyersResponse) {
        getCalling(requireContext(), model.phone)
    }

    override fun onClickBuyingRequest(model: BuyersResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL,model)
        launchActivity(BuyerOrderActivity::class.java,Constants.DefaultConstant.BUNDLE_KEY,b)

    }

    override fun onClickPurchaseOrder(model: BuyersResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL,model)
        launchActivity(BuyerOrderActivity::class.java,Constants.DefaultConstant.BUNDLE_KEY,b)
    }

    override fun onClickTotalOrder(model: BuyersResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL,model)
        launchActivity(BuyerOrderActivity::class.java,Constants.DefaultConstant.BUNDLE_KEY,b)    }

}