package com.emines_employee.screen.dashboard

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.BaseResponse
import com.emines_employee.databinding.ActivityMainBinding
import com.emines_employee.model.response.UserResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.BuyersFragment
import com.emines_employee.screen.dashboard.home.HomeFragment
import com.emines_employee.screen.dashboard.orders.OrdersFragment
import com.emines_employee.screen.dashboard.profile.ProfileFragment
import com.emines_employee.screen.dashboard.seller.SellersFragment
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.requestStoragePermission
import com.emines_employee.util.requestStoragePermissionAbove32
import org.koin.core.component.inject

class MainActivity : BaseActivity(), ActivityMainListener {
    private var isBackPressAgain = false
    private lateinit var mainListener: ActivityMainListener
    private val mViewModel: MainViewModel by inject()

    companion object {
        lateinit var mainActivityBinding: ActivityMainBinding
        const val HOME_FRAGMENT = 1
        const val BUYER_FRAGMENT = 2
        const val SELLER_FRAGMENT = 3
        const val ORDER_FRAGMENT = 4
        const val PROFILE_FRAGMENT = 5

    }

    override val layoutId: Int
        get() = R.layout.activity_main

    private var onBackPressAgain: Boolean = false

    private val homeFragment: HomeFragment by lazy {
        HomeFragment()
    }
    private val buyerFragment: BuyersFragment by lazy {
        BuyersFragment()
    }

    private val sellerFragment: SellersFragment by lazy {
        SellersFragment()
    }

    private val ordersFragment: OrdersFragment by lazy {
        OrdersFragment()
    }

    private val profileFragment: ProfileFragment by lazy {
        ProfileFragment()
    }

    override fun onCreateInit(binding: ViewDataBinding?) {
        mainActivityBinding = binding as ActivityMainBinding
        mainListener = this@MainActivity
        mainListener.onReplaceFragment(homeFragment)
        onSelectBtn(HOME_FRAGMENT)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestStoragePermissionAbove32(this@MainActivity)
        } else {
            requestStoragePermission(this@MainActivity)
        }




        mViewModel.hitProfileApi(mPref.getUserDetail()?.id!!)
        mViewModel.profileResponse.observe(this@MainActivity, profileDataObserver)

        mainActivityBinding.apply {
            customBottomBarLayout.clHomeNav.setOnClickListener {
                mainListener.onReplaceFragment(homeFragment)
                onSelectBtn(HOME_FRAGMENT)

            }
            customBottomBarLayout.clBuyersNav.setOnClickListener {
                mainListener.onReplaceFragment(buyerFragment)
                onSelectBtn(BUYER_FRAGMENT)


            }
            customBottomBarLayout.clOrderNav.setOnClickListener {
                //mToast("Access Not Allowed !!")
               // mainListener.onReplaceFragment(ordersFragment)
                onSelectBtn(ORDER_FRAGMENT)


            }
            customBottomBarLayout.clSellersNav.setOnClickListener {
              //  mToast("Access Not Allowed !!")
                mainListener.onReplaceFragment(sellerFragment)
                onSelectBtn(SELLER_FRAGMENT)

            }
            customBottomBarLayout.clAccountNav.setOnClickListener {
                mainListener.onReplaceFragment(profileFragment)
                onSelectBtn(PROFILE_FRAGMENT)

            }
        }
    }


    private fun onSelectBtn(bottomTabItem: Int) {
        defaultIconsAndTextColor()
        mainActivityBinding.customBottomBarLayout.apply {
            when (bottomTabItem) {
                1 -> {
                    tvHomeNav.setTextColor(resources.getColor(R.color.blue_color_2, null))
                    ivHomeIcon.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_selected_home, null)
                }

                2 -> {
                    tvBuyersNav.setTextColor(resources.getColor(R.color.blue_color_2, null))
                    ivBuyersIcon.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_client, null)
                }

                3 -> {
                    tvSellersNav.setTextColor(resources.getColor(R.color.blue_color_2, null))
                    ivSellersIcon.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_unselected_pickup,
                            null
                        )
                }

                4 -> {
                    tvOrderNav.setTextColor(resources.getColor(R.color.blue_color_2, null))
                    ivOrderIcon.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_order, null)
                }

                5 -> {
                    tvAccountNav.setTextColor(resources.getColor(R.color.blue_color_2, null))
                    ivAccountIcon.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_account, null)
                }
            }

        }
    }

    private fun defaultIconsAndTextColor() {
        mainActivityBinding.customBottomBarLayout.apply {
            tvHomeNav.setTextColor(resources.getColor(R.color.default_text_color, null))
            tvBuyersNav.setTextColor(resources.getColor(R.color.default_text_color, null))
            tvSellersNav.setTextColor(resources.getColor(R.color.default_text_color, null))
            tvOrderNav.setTextColor(resources.getColor(R.color.default_text_color, null))
            tvAccountNav.setTextColor(resources.getColor(R.color.default_text_color, null))
            ivHomeIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_selected_home, null)
            ivBuyersIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselect_client, null)
            ivSellersIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_unselected_pickup, null)
            ivOrderIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_order, null)
            ivAccountIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_account, null)

        }


    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            if (isBackPressAgain) {
                super.onBackPressed()
                onBackPressedDispatcher.onBackPressed()
            } else {
                isBackPressAgain = true
                Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    isBackPressAgain = false
                }, 3000)
            }
        }
    }

    override fun onReplaceFragment(fragment: Fragment) {
        replaceFragment(
            R.id.flMainContainer,
            fragment
        )
    }


    private val profileDataObserver: Observer<ApiResponse<BaseResponse<UserResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }
                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    val ud = it.data?.data
                    mLog(ud.toString())
                    mPref.setUserDetail(ud)
                }
                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(it.error?.message!!)
                }
            }

        }
    }


}