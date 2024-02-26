package com.emines_employee.screen.dashboard.buyer.createrequest.selectcategory

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.response.BuyerOrderRequest
import com.emines_employee.model.response.CategoryResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.mLog
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepo : BaseRepository() {
    fun executeCategoryListApi(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<CategoryResponse>>>
    ) {
        val call = apiService.getCategoryList()
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<CollectionBaseResponse<CategoryResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<CategoryResponse>>,
                response: Response<CollectionBaseResponse<CategoryResponse>>
            ) {
                try {
                    if (response.code() == Constants.NetworkConstant.API_SUCCESS) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        val jObjError = JSONObject(
                            response.errorBody()!!.string()
                        )
                        val jObjErrorMessage = jObjError.getString("message")
                        val jObjErrorCode = jObjError.getString("code")
                        mLog("$jObjErrorMessage code $jObjErrorCode")
                        responseLiveData.postValue(ApiResponse.error(Throwable(jObjErrorMessage)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<CategoryResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("Buyers Repo onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


    fun executeBuyerOrderRequestApi(
        req: BuyerOrderRequest,
        responseLiveData: MutableLiveData<ApiResponse<SuccessMsgResponse>>
    ) {
        val call = apiService.buyerOrderRequest(req)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<SuccessMsgResponse> {
            override fun onResponse(
                call: Call<SuccessMsgResponse>,
                response: Response<SuccessMsgResponse>
            ) {
                try {
                    if (response.body()?.status!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()!!.message)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(
                call: Call<SuccessMsgResponse>,
                t: Throwable
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