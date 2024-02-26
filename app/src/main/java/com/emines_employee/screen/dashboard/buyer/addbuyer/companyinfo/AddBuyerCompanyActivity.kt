package com.emines_employee.screen.dashboard.buyer.addbuyer.companyinfo

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.CompanyStatusAdapter
import com.emines_employee.base.BaseActivity
import com.emines_employee.databinding.ActivityAddBuyerCompanyInfoBinding
import com.emines_employee.model.CompanyStatus
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.screen.dashboard.buyer.addbuyer.kyc.AddBuyerKYCActivity
import com.emines_employee.util.mLog

class AddBuyerCompanyActivity : BaseActivity(), CompanyStatusAdapter.OnCheckCompanyStatusListener {
    private lateinit var mBind: ActivityAddBuyerCompanyInfoBinding

    override val layoutId = R.layout.activity_add_buyer_company_info
    private var companyStatus: String? = null

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAddBuyerCompanyInfoBinding

        mBind.apply {
            toolbarBBA.tvToolBarTitle2.text = String.format(
                "%s %s",
                getString(R.string.company_info),
                "(${getString(R.string.buyer)})"
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
                        mPref.getBuyerDetail()?.let {
                            val addBuyerRes = AddBuyerRequest().apply {
                                buyer_type = it.buyer_type
                                full_name = it.full_name
                                email = it.email
                                phone = it.phone
                                profile_image = it.profile_image
                                company_name = etCName.text.toString()
                                company_email = etCEmail.text.toString()
                                company_phone = etCMobile.text.toString()
                                website = etCWebsite.text.toString()


                            }
                            mPref.addBuyerDetail(addBuyerRes)
                        }

                        launchActivity(AddBuyerKYCActivity::class.java)
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


    private fun getCompanyStatus(): MutableList<CompanyStatus> {
        val list = mutableListOf<CompanyStatus>()
        list.add(CompanyStatus(1, getString(R.string.company_status_private_ltd)))
        list.add(CompanyStatus(2, getString(R.string.company_status_public)))
        list.add(CompanyStatus(3, getString(R.string.company_status_firm)))
        list.add(CompanyStatus(4, getString(R.string.company_status_partnership)))
        list.add(CompanyStatus(5, getString(R.string.company_status_proprietor)))
        list.add(CompanyStatus(6, getString(R.string.company_status_msme)))
        return list
    }

    override fun onCheck(title: String) {
        companyStatus = title
        mLog("Company status $title")
    }
}