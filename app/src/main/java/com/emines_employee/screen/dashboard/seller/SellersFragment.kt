package com.emines_employee.screen.dashboard.seller

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.BuyerSellerAdapter
import com.emines_employee.adapter.listener.OnCreateBuyerSellerListener
import com.emines_employee.base.BaseFragment
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.FragmentSellerBinding
import com.emines_employee.model.request.AddSellerRequest
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.seller.addseller.contactinfo.AddSellerContactActivity
import com.emines_employee.screen.dashboard.seller.createorderrequest.selectcategory.SellerSelectCategoryActivity
import com.emines_employee.screen.dashboard.seller.sellerorder.SellerOrdersActivity
import com.emines_employee.screen.dashboard.seller.viewseller.contact.ViewSellerContactActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.getCalling
import com.emines_employee.util.isConnectionAvailable
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import org.koin.core.component.inject

class SellersFragment : BaseFragment(), OnCreateBuyerSellerListener {

    private lateinit var mBind: FragmentSellerBinding
    private val mViewModel: SellerViewModel by inject()
    private var buyerList = mutableListOf<BuyersResponse>()

    private var mContext: Context? = null

    override fun getLayoutId() = R.layout.fragment_seller

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    override fun onCreateViewInit(binding: ViewDataBinding, view: View) {
        mBind = binding as FragmentSellerBinding
        mBind.apply {
            toolbarSeller.apply {
                ivToolBarBack.visibility = View.GONE
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                    text = getString(R.string.seller_list)
                }
            }

            tvAddSellerBtn.setOnClickListener {
                mPref.addSellerDetail(AddSellerRequest())
                launchActivity(AddSellerContactActivity::class.java)
            }
            rvSellers.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(
                        mContext!!.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
            }

            if (isConnectionAvailable()) {
                mViewModel.hitSellerListApi()
                mViewModel.getSellerListResponse()
                    .observe(requireActivity(), sellerListResponseObserver)
            } else {
                mToast(getString(R.string.oops_no_internet_available))
            }

        }
    }

    private val sellerListResponseObserver: Observer<ApiResponse<CollectionBaseResponse<BuyersResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    buyerList.clear()
                    buyerList = it.data?.data as MutableList<BuyersResponse>
                    mLog("Buyers  $buyerList")
                    setSellerList(buyerList)
                    mBind.tvBuyerAndOrder.text =
                        String.format("%s", "Manage Seller (${buyerList.size}) & Orders")
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message.toString())
                }
            }
        }
    }

    private fun setSellerList(buyerList: MutableList<BuyersResponse>) {
        mBind.rvSellers.apply {
            layoutManager =
                LinearLayoutManager(
                    mContext!!.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter =
                BuyerSellerAdapter(
                    buyerList,
                    mContext!!.applicationContext,
                    this@SellersFragment,
                    false
                )
        }

    }

    /*private fun getList(): MutableList<User> {
        val list = mutableListOf<User>()
        list.add(User(1, getString(R.string.seller), "kuldeep Singh"))
        list.add(User(2, getString(R.string.seller), "kuldeep Singh"))
        list.add(User(3, getString(R.string.seller), "kuldeep Singh"))
        list.add(User(4, getString(R.string.seller), "kuldeep Singh"))
        list.add(User(5, getString(R.string.seller), "kuldeep Singh"))
        list.add(User(6, getString(R.string.seller), "kuldeep Singh"))
        return list
    }*/

    override fun onUpdate(model: BuyersResponse) {

    }

    override fun onCreateClick(model: BuyersResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(
            SellerSelectCategoryActivity::class.java,
            Constants.DefaultConstant.BUNDLE_KEY,
            b
        )

    }

    override fun onViewClick(model: BuyersResponse) {
        model.isEditBuyer = false
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(
            ViewSellerContactActivity::class.java,
            Constants.DefaultConstant.BUNDLE_KEY,
            b
        )

    }

    override fun onMapClick(model: BuyersResponse) {
    }

    override fun onCallClick(model: BuyersResponse) {
        getCalling(requireContext(), model.phone)
    }

    override fun onClickBuyingRequest(model: BuyersResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(SellerOrdersActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)

    }

    override fun onClickPurchaseOrder(model: BuyersResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(SellerOrdersActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)

    }

    override fun onClickTotalOrder(model: BuyersResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, model)
        launchActivity(SellerOrdersActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b)

    }

    /*   override fun onUpdate(model: BuyersResponse) {
           launchActivity(
               BuyerContactPersonActivity::class.java,
               "Title",
               getString(R.string.seller)
           )
       }

       override fun onCreateClick(model: BuyersResponse) {
           launchActivity(SelectCategoryActivity::class.java)
       }

       override fun onViewClick(model: BuyersResponse) {

           launchActivity(
               BuyerContactPersonActivity::class.java,
               "Title",
               getString(R.string.seller)
           )
       }

       override fun onMapClick(model: BuyersResponse) {
           Toast.makeText(requireContext(), "working on it", Toast.LENGTH_SHORT).show()
       }

       override fun onCallClick(model: BuyersResponse) {
           Toast.makeText(requireContext(), "working on it", Toast.LENGTH_SHORT).show()
       }

       */

}