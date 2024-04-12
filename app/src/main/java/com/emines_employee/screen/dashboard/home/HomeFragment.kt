package com.emines_employee.screen.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.emines_employee.R
import com.emines_employee.base.BaseFragment
import com.emines_employee.base.BaseResponse1
import com.emines_employee.databinding.FragmentHomeBinding
import com.emines_employee.model.response.DashboardResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.isConnectionAvailable
import com.emines_employee.util.mToast
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private val mViewModel: HomeViewModel by inject()
    private val userDetail = mPref.getUserDetail()

    override fun getLayoutId() = R.layout.fragment_home
    override fun onCreateViewInit(binding: ViewDataBinding, view: View) {
        homeBinding = binding as FragmentHomeBinding
        mViewModel.mFragment = this@HomeFragment
        homeBinding.mViewModel = mViewModel

        if (isConnectionAvailable()) {
            mViewModel.hitDashboardApi(mPref.getUserDetail()?.id!!)
            mViewModel.getHomeDashboardApiResponse().observe(this@HomeFragment, dashboardDataResponse)
        } else {
            mToast(getString(R.string.oops_no_internet_available))
        }






        homeBinding.apply {
            llcHomeBuyers.setOnClickListener {
                //
                // replaceFragment(R.id.flMainContainer,BuyersFragment())
            }

            llcHomeSeller.setOnClickListener {
                // replaceFragment(R.id.flMainContainer,SellersFragment())
            }

            /* rvInfoProfileHome.apply {
                 layoutManager =
                     LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                 adapter = HomeSliderAdapter(getSliderList(), requireContext())
             }

             rvInfo2ProfileHome.apply {
                 layoutManager =
                     LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                 adapter = HomeSliderAdapter(getSliderList(), requireContext())
             }
 */
            userDetail?.let {
                Glide.with(requireContext()).load(it.profilePic).placeholder(R.drawable.profile_img)
                    .into(ivProfileHomeImg)
                tvHomeUName.text = String.format("%s %s", it.name, it.lastName)
                tvHomeEmail.text = it.email
                tvHomeMobile.text = it.phone
                currentAddressHome.text = it.address
            }


        }

    }

    private val dashboardDataResponse = Observer<ApiResponse<BaseResponse1<DashboardResponse>>> {
        when (it.status) {
            ApiResponse.Status.LOADING -> {
                showProgress()
            }

            ApiResponse.Status.SUCCESS -> {
                hideProgress()
                homeBinding.apply {
                    val model = it.data?.data
                    model?.let {
                        tvBuyerCount.text = it.buyers.toString()
                        tvBuyingRequestCountBuyer.text = it.buying_requests.toString()
                        tvApprovedCountBuyer.text = it.approved_requests.toString()
                        tvCancelledCountBuyer.text = it.cancelled_requests.toString()
                        tvPOReceivedCountBuyer.text = it.po_recieved_requests.toString()
                        tvOrdersCountBuyer.text = it.total_orders.toString()
                        tvAssignedOrderCountBuyer.text = it.assigned_requests.toString()
                        tvDeniedOrderCountBuyer.text = it.denied_orders.toString()
                        tvInProcessOrderCountBuyer.text = it.inprocessOrders.toString()
                        tvDeliveredOrderCountBuyer.text = it.deliveredOrders.toString()
                    }


                }
            }

            ApiResponse.Status.ERROR -> {
                hideProgress()
                mToast(it.error?.message.toString())
            }
        }
    }

    private fun getSliderList(): MutableList<String> {
        val list = mutableListOf<String>()
        list.add(getString(R.string.explore_info))
        list.add(getString(R.string.explore_info))
        list.add(getString(R.string.explore_info))
        list.add(getString(R.string.explore_info))
        return list
    }
}