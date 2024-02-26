package com.emines_employee.screen.dashboard.buyer.createrequest.selectcategory

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.response.BuyerOrderRequest
import com.emines_employee.model.response.CategoryResponse
import com.emines_employee.network.ApiResponse

class CategoryViewModel(private val repo: CategoryRepo) : BaseViewModel() {
    private val categoryResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<CategoryResponse>>>()

    private val buyerOrderRequestResponse =
        MutableLiveData<ApiResponse<SuccessMsgResponse>>()

    fun hitCategoryListApi() {
        repo.executeCategoryListApi(categoryResponse)
    }
    fun getCategoryListResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<CategoryResponse>>> {
        return categoryResponse
    }


    fun hitBuyerOrderRequestApi(request: BuyerOrderRequest) {
        repo.executeBuyerOrderRequestApi(request,buyerOrderRequestResponse)
    }
    fun getBuyerOrderRequestResponse(): MutableLiveData<ApiResponse<SuccessMsgResponse>> {
        return buyerOrderRequestResponse
    }


}