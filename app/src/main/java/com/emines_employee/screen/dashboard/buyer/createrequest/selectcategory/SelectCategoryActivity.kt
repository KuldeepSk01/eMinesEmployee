package com.emines_employee.screen.dashboard.buyer.createrequest.selectcategory

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.emines_employee.R
import com.emines_employee.adapter.AddBuyerGoodsAdapter
import com.emines_employee.adapter.SellCategoryAdapter
import com.emines_employee.adapter.listener.OnSellCategoryClickListener
import com.emines_employee.base.BaseActivity
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.databinding.ActivitySelectCategoryBinding
import com.emines_employee.model.BuyerGoods
import com.emines_employee.model.CreateBuyerOrderRequest
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.model.response.CategoryResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.screen.dashboard.buyer.createrequest.address.AddressListActivity
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import com.emines_employee.util.mToast
import com.emines_employee.util.serializable
import org.koin.core.component.inject

class SelectCategoryActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener,
    OnSellCategoryClickListener {
    private lateinit var selectCatBinding: ActivitySelectCategoryBinding
    private val mViewModel: CategoryViewModel by inject()
    private var categoryModel: CategoryResponse? = null
    private var buyerReqList = mutableListOf<BuyerGoods>()
    private var buyerReqListSet = mutableSetOf<BuyerGoods>()
    private lateinit var addBuyerGoodsAdapter: AddBuyerGoodsAdapter

    private var categoryList = mutableListOf<CategoryResponse>()
    private lateinit var categoryAdpater : SellCategoryAdapter


    override val layoutId: Int
        get() = R.layout.activity_select_category

    override fun onCreateInit(binding: ViewDataBinding?) {
        selectCatBinding = binding as ActivitySelectCategoryBinding
        selectCatBinding.apply {
            val buyerDetail = intent.getBundleExtra(Constants.DefaultConstant.BUNDLE_KEY)
                ?.serializable<BuyersResponse>(Constants.DefaultConstant.MODEL_DETAIL)

           categoryAdpater =  SellCategoryAdapter(
                categoryList, this@SelectCategoryActivity, this@SelectCategoryActivity
            )
            mViewModel.hitCategoryListApi()
            mViewModel.getCategoryListResponse()
                .observe(this@SelectCategoryActivity, categoryResponseObserver)

            toolbarSelectCategory.apply {
                ivToolBarBack.visibility = View.GONE
                tvToolBarTitle.apply {
                    setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
                    text = context.getString(R.string.i_want_to_buy)
                }
            }

            /*tvSwitchSC.apply {
                isChecked = false
                setOnCheckedChangeListener(this@SelectCategoryActivity)
            }*/

            /*spChooseUnitSC.setOnClickListener {
                *//*  dropDownPopup(
                      this@SelectCategoryActivity,
                      it,
                      R.menu.menu_weight_typee,
                      object : OnDropDownListener {
                          override fun onDropDownClick(item: String) {
                              spChooseUnitSC.text = item
                          }

                      }).show()*//*
            }
*/
            bottomButtons.apply {
                tvFirstBtn.text = getString(R.string.back_cap)
                tvSecondBtn.text = getString(R.string.continue_text)
                tvFirstBtn.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvSecondBtn.setOnClickListener {
                    if (buyerReqList.size == 0) {
                        mToast(getString(R.string.please_add_at_least_one_good))
                    } else {
                        val req = CreateBuyerOrderRequest()
                        req.buyer = buyerDetail
                        req.goodsList = buyerReqList

                        val b = Bundle()
                        b.putSerializable(Constants.DefaultConstant.MODEL_DETAIL, req)
                        launchActivity(
                            AddressListActivity::class.java, Constants.DefaultConstant.BUNDLE_KEY, b
                        )
                    }
                }
            }

            tvAddBuyerOrderReq.setOnClickListener {

                if (categoryModel != null) {
                    if (etChooseUnitSC.text.toString().isEmpty()) {
                        mToast(getString(R.string.please_select_weight_of_category))
                    } else {
                        if (etRatePerKgSC.text.toString().isEmpty()) {
                            mToast(getString(R.string.please_select_amount))
                        } else {
                            //buyerReqList = buyerReqListSet.toMutableList()
                            if (buyerReqList.size < 4) {
                                val req = BuyerGoods().apply {
                                    id = categoryModel!!.id
                                    descriptionOdGoods = categoryModel?.categoryname
                                    termsCode = categoryModel?.termsCode
                                    purchaseQuantity = etChooseUnitSC.text.toString()
                                    unitOfQuantity = spChooseUnitSC.text.toString()
                                    rate = etRatePerKgSC.text.toString()

                                    totalAmount = totalAmountWithoutGst(
                                        etChooseUnitSC.text.toString().toInt(),
                                        etRatePerKgSC.text.toString().toInt(),
                                    ).toString()
                                    gst = categoryModel!!.categoryGst.toString()

                                    totalAmountWithGst = totalAmountWithGst(
                                        totalAmountWithoutGst(
                                            etChooseUnitSC.text.toString().toInt(),
                                            etRatePerKgSC.text.toString().toInt(),
                                        ),
                                        categoryModel!!.categoryGst.toInt(),
                                    ).toString()
                                }
                                buyerReqListSet.add(req)

                            } else {
                                mToast(getString(R.string.you_reached_maximum_limit_4))
                            }


                            buyerReqList = buyerReqListSet.toMutableList()
                            addBuyerGoodsAdapter =
                                AddBuyerGoodsAdapter(buyerReqList, this@SelectCategoryActivity)
                            rvBuyerOrderReq.apply {
                                layoutManager = LinearLayoutManager(this@SelectCategoryActivity)
                                adapter = addBuyerGoodsAdapter
                            }
                            addBuyerGoodsAdapter.notifyDataSetChanged()

                            tvTotalAMount.text = String.format(
                                "%s %s",
                                getString(R.string.indian_rupee_symbol),
                                0
                            )
                            tvTotalAmountWithGst.text = String.format(
                                "%s %s",
                                getString(R.string.indian_rupee_symbol),
                                0
                            )

                            etRatePerKgSC.setText("")
                            etChooseUnitSC.setText("")
                            categoryModel = null
                            categoryAdpater.notifyDataSetChanged()

                        }
                    }

                } else {
                    mToast(getString(R.string.please_select_category))
                }

            }

            etRatePerKgSC.doOnTextChanged { text, start, before, count ->
                if (categoryModel != null) {
                    mLog(" text $text start $start before  $before count $count")
                    if (etChooseUnitSC.text.toString().isEmpty()) {
                        mToast(getString(R.string.please_select_weight_of_category))
                       // etRatePerKgSC.isClickable = false
                    } else {
                        if (!text?.isNullOrEmpty()!!) {
                            val amountWithoutGst = totalAmountWithoutGst(
                                etChooseUnitSC.text.toString().toInt(),
                                etRatePerKgSC.text.toString().toInt(),
                            )
                            val totalPriceWithGst = totalAmountWithGst(
                                amountWithoutGst,
                                categoryModel!!.categoryGst.toInt(),
                            ).toString()

                            selectCatBinding.tvTotalAMount.text = String.format(
                                "%s %s",
                                getString(R.string.indian_rupee_symbol),
                                amountWithoutGst.toString()
                            )
                            selectCatBinding.tvTotalAmountWithGst.text = String.format(
                                "%s %s",
                                getString(R.string.indian_rupee_symbol),
                                totalPriceWithGst.toString()
                            )

                        } else {
                           /* val tam = totalAmountWithoutGst(etChooseUnitSC.text.toString().toInt(), 1)
                            selectCatBinding.tvTotalAMount.text = String.format(
                                "%s %s",
                                getString(R.string.indian_rupee_symbol), tam
                            )
                            selectCatBinding.tvTotalAmountWithGst.text = String.format(
                                "%s %s", getString(R.string.indian_rupee_symbol), totalAmountWithGst(
                                    tam,
                                    categoryModel!!.categoryGst.toInt(),
                                ).toString()
                            )*/
                        }
                    }
                }else{
                   // etRatePerKgSC.setText("")
                    mToast(getString(R.string.please_select_category))
                }





            }

        }


    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {/* if (isChecked) {
             selectCatBinding.apply {
                 clQuantityLayout.visibility = View.VISIBLE
                 clWeightLayout.visibility = View.GONE
                 etChooseUnitSC.setText("")
                 etTotalPicsSC.setText("")
                 etRatePerKgSC.setText("")

             }

         } else {
             selectCatBinding.apply {
                 clQuantityLayout.visibility = View.GONE
                 clWeightLayout.visibility = View.VISIBLE
                 etChooseUnitSC.setText("")
                 etTotalPicsSC.setText("")
                 etRatePerKgSC.setText("")

             }
         }*/
    }


    override fun onCategoryClick(category: CategoryResponse) {
        categoryModel = category
        selectCatBinding.apply {
            //etRatePerKgSC.setText("")
            //etChooseUnitSC.setText("")
            tvCategoryTermsCode.apply {
                visibility = View.VISIBLE
                text = String.format("%s : %s", getString(R.string.terms_code), category.termsCode)
            }
        }

    }


    private val categoryResponseObserver =
        Observer<ApiResponse<CollectionBaseResponse<CategoryResponse>>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    categoryList = it.data?.data as MutableList<CategoryResponse>
                    setCategoryList(categoryList)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                }
            }
        }


    private fun setCategoryList(categoryList: MutableList<CategoryResponse>) {
        addBuyerGoodsAdapter = AddBuyerGoodsAdapter(buyerReqList, this@SelectCategoryActivity)
        selectCatBinding.rvSellCategory.apply {
            layoutManager = GridLayoutManager(this@SelectCategoryActivity, 4)
            categoryAdpater = SellCategoryAdapter(
                categoryList, this@SelectCategoryActivity, this@SelectCategoryActivity
            )
            adapter = categoryAdpater
        }

    }

    private fun totalAmountWithGst(totalAmount: Long, percent: Int): Long {
        val gst = (totalAmount * percent) / 100
        return gst.plus(totalAmount)
    }

    private fun totalAmountWithoutGst(matricTon: Int, ratePerKg: Int): Long {
        val totalAmountCal = matricTon * ratePerKg * 1000
        return totalAmountCal.toLong()

    }


}