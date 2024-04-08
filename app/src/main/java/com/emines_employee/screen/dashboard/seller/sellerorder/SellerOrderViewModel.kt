package com.emines_employee.screen.dashboard.seller.sellerorder

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.model.request.GetBuyerOrderRequest
import com.emines_employee.model.request.GetSellerOrderRequest
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.network.ApiResponse

class SellerOrderViewModel(private val repo: SellingOrderRepo) : BaseViewModel() {
    private val mResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<RequestOrderResponse>>>()

    fun hitSellerOrderListListApi(request: GetSellerOrderRequest) {
        repo.executeRequestSellerOrderListApi(request, mResponse)
    }

    fun getSellerOrderListResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<RequestOrderResponse>>> {
        return mResponse
    }


}