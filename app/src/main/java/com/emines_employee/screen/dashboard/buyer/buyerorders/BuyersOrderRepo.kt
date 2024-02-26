package com.emines_employee.screen.dashboard.buyer.buyerorders

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.model.request.GetBuyerOrderRequest
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BuyersOrderRepo : BaseRepository() {
    fun executeRequestBuyerOrderListApi(
        req: GetBuyerOrderRequest,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<RequestOrderResponse>>>
    ) {
        val call = apiService.getBuyerOrdersList(req)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<CollectionBaseResponse<RequestOrderResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<RequestOrderResponse>>,
                response: Response<CollectionBaseResponse<RequestOrderResponse>>
            ) {
                try {
                    if (response.body()?.status!!) {
                        mLog("Category Repo : ${response.body()!!.toString()}")
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()!!.message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<RequestOrderResponse>>, t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("Category Repo onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


}