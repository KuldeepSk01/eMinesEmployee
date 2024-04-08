package com.emines_employee.screen.dashboard.seller.createorderrequest.selectcategory

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.BuyerAddressRequest
import com.emines_employee.model.response.BuyerAddressResponse
import com.emines_employee.model.response.BuyerOrderRequest
import com.emines_employee.model.response.CategoryResponse
import com.emines_employee.model.response.SellerOrderRequest
import com.emines_employee.network.ApiResponse

class SellerCategoryViewModel(private val repo: SellerCategoryRepo) : BaseViewModel() {
    private val categoryResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<CategoryResponse>>>()

    private val sellerOrderRequestResponse =
        MutableLiveData<ApiResponse<SuccessMsgResponse>>()

    private val sellerAddressListResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<BuyerAddressResponse>>>()


    fun hitCategoryListApi() {
        repo.executeCategoryListApi(categoryResponse)
    }
    fun getCategoryListResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<CategoryResponse>>> {
        return categoryResponse
    }


    fun hitSellerOrderRequestApi(request: SellerOrderRequest) {
        repo.executeSellerOrderRequestApi(request,sellerOrderRequestResponse)
    }
    fun getSellerOrderRequestResponse(): MutableLiveData<ApiResponse<SuccessMsgResponse>> {
        return sellerOrderRequestResponse
    }


    fun hitSellerAddressApi(sellerId:Int) {
        repo.executeSellerAddressListApi(sellerId, sellerAddressListResponse)
    }

    fun getSellerAddressResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<BuyerAddressResponse>>> {
        return sellerAddressListResponse
    }


}