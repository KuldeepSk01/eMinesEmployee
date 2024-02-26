package com.emines_employee.screen.dashboard.buyer.buyerorders

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.model.request.GetBuyerOrderRequest
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.network.ApiResponse

class BuyerOrderViewModel(private val repo: BuyersOrderRepo) : BaseViewModel() {
    private val mResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<RequestOrderResponse>>>()

    fun hitBuyerOrderListListApi(request: GetBuyerOrderRequest) {
        repo.executeRequestBuyerOrderListApi(request, mResponse)
    }

    fun getBuyerOrderListResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<RequestOrderResponse>>> {
        return mResponse
    }


}