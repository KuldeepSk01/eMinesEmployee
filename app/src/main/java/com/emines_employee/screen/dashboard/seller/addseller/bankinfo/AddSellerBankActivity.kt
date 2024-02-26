package com.emines_employee.screen.dashboard.seller.addseller.bankinfo

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.emines_employee.R
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.databinding.ActivityAddSellerBankBinding
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.request.AddSellerRequest
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.MainActivity
import com.emines_employee.screen.dashboard.seller.SellerViewModel
import com.emines_employee.screen.dashboard.seller.addseller.contactinfo.AddSellerContactActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.IsValidBuyerSellerField
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import org.koin.core.component.inject

const val PHONE_EXIST = "Phone Already Exits."
const val EMAIL_EXIST = "email Already Exits."

class AddSellerBankActivity : BaseActivity() {
    private lateinit var mBind: ActivityAddSellerBankBinding
    private val mViewModel: SellerViewModel by inject()


    override val layoutId = R.layout.activity_add_seller_bank

    override fun onCreateInit(binding: ViewDataBinding?) {
        mBind = binding as ActivityAddSellerBankBinding

        mBind.apply {
            toolbarSellerBank.tvToolBarTitle2.text = String.format(
                "%s %s",
                getString(R.string.bank_account),
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
                            val addSellerRequest = AddSellerRequest().apply {
                                seller_type = it.seller_type
                                userId = mPref.getUserDetail()?.id!!
                                full_name = it.full_name
                                email = it.email
                                phone = it.phone
                                company_name = it.company_name
                                company_email = it.company_email
                                company_phone = it.company_phone
                                website = it.website
                                pan_no = it.pan_no
                                aadhar_no = it.aadhar_no
                                gst_no = it.gst_no
                                pan_file_path = it.pan_file_path
                                aadhar_file_path = it.aadhar_file_path
                                gst_file_path = it.gst_file_path
                                profile_image = it.profile_image
                                bank_name = etSellerBankName.text.toString()
                                ifsc_code = etSellerBankIFSC.text.toString()
                                account_no = etSellerBankAccount.text.toString()

                            }
                            mPref.addSellerDetail(addSellerRequest)
                        }
                        val req = mPref.getSellerDetail()
                        mViewModel.hitSaveSellerApi(req!!)
                        mViewModel.getSaveSellerResponse()
                            .observe(this@AddSellerBankActivity, saveBuyerResponseObserver)
                        mLog("Buyer Detail is $req")
                    }
                }
            }
        }
    }


    private val saveBuyerResponseObserver: Observer<ApiResponse<SuccessMsgResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    mToast(it.data?.message.toString())
                    mPref.put(
                        Constants.PreferenceConstant.IS_ADD_BUYER_INVALID,
                        Constants.PreferenceConstant.IS_EMAIL_OR_MOBILE_VALID
                    )
                    mPref.addBuyerDetail(AddBuyerRequest())
                    launchActivity(MainActivity::class.java)
                }
                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    val err = it.error?.message.toString()
                    mToast(err)

                    if (err == PHONE_EXIST || err == EMAIL_EXIST) {
                        IsValidBuyerSellerField = true
                        launchActivity(AddSellerContactActivity::class.java)
                    }
                }
            }
        }
    }
}