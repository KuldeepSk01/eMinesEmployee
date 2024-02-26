package com.emines_employee.screen.dashboard.seller.addseller.companyinfo

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.CompanyStatusAdapter
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityAddBuyerCompanyInfoBinding
import com.emines_employee.databinding.ActivityAddSellerCompanyBinding
import com.emines_employee.model.CompanyStatus
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.request.AddSellerRequest
import com.emines_employee.screen.dashboard.buyer.addbuyer.kyc.AddBuyerKYCActivity
import com.emines_employee.screen.dashboard.seller.addseller.kyc.AddSellerKYCActivity
import com.emines_employee.util.mLog

class AddSellerCompanyActivity : BaseActivity(), CompanyStatusAdapter.OnCheckCompanyStatusListener {
    private lateinit var mBind: ActivityAddSellerCompanyBinding

    override val layoutId = R.layout.activity_add_seller_company
    private var companyStatus: String? = null

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAddSellerCompanyBinding

        mBind.apply {
            toolbarSellerCompany.tvToolBarTitle2.text = String.format(
                "%s %s",
                getString(R.string.company_info),
                "(${getString(R.string.seller)})"
            )
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
                        mPref.getSellerDetail()?.let {
                            val addBuyerRes = AddSellerRequest().apply {
                                seller_type = it.seller_type
                                full_name = it.full_name
                                email = it.email
                                phone = it.phone
                                profile_image = it.profile_image
                                company_name = etSellerCompanyName.text.toString()
                                company_email = etSellerCompanyEmail.text.toString()
                                company_phone = etSellerCompanyMobile.text.toString()
                                website = etSellerCompanyWebsite.text.toString()

                            }
                            mPref.addSellerDetail(addBuyerRes)
                        }
                        launchActivity(AddSellerKYCActivity::class.java)
                    }
                }
/*
                rvCompanyStatus.apply {
                    itemAnimator = DefaultItemAnimator()
                    layoutManager = LinearLayoutManager(
                        this@AddBuyerCompanyActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter = CompanyStatusAdapter(
                        getCompanyStatus(),
                        this@AddBuyerCompanyActivity,
                        this@AddBuyerCompanyActivity
                    )
                }*/
            }
        }
    }


  /*  private fun getCompanyStatus(): MutableList<CompanyStatus> {
        val list = mutableListOf<CompanyStatus>()
        list.add(CompanyStatus(1, getString(R.string.company_status_private_ltd)))
        list.add(CompanyStatus(2, getString(R.string.company_status_public)))
        list.add(CompanyStatus(3, getString(R.string.company_status_firm)))
        list.add(CompanyStatus(4, getString(R.string.company_status_partnership)))
        list.add(CompanyStatus(5, getString(R.string.company_status_proprietor)))
        list.add(CompanyStatus(6, getString(R.string.company_status_msme)))
        return list
    }
*/
    override fun onCheck(title: String) {
        companyStatus = title
        mLog("Company status $title")
    }
}