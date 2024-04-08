package com.emines_employee.screen.dashboard.buyer

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.response.BuyerAddressResponse
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.Constants
import com.emines_employee.util.MultipartHelper
import com.emines_employee.util.mLog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class BuyersRepo : BaseRepository() {
    fun executeBuyersListApi(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<BuyersResponse>>>
    ) {
        val call = apiService.buyersList()
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<CollectionBaseResponse<BuyersResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<BuyersResponse>>,
                response: Response<CollectionBaseResponse<BuyersResponse>>
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
                        mLog(jObjErrorMessage)
                        mLog(jObjErrorCode)
                        responseLiveData.postValue(ApiResponse.error(Throwable(jObjErrorMessage)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<BuyersResponse>>,
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

    fun executeBuyerAddressListApi(
        buyerId: Int,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<BuyerAddressResponse>>>
    ) {
        val call = apiService.buyersAddressList(buyerId)
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<CollectionBaseResponse<BuyerAddressResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<BuyerAddressResponse>>,
                response: Response<CollectionBaseResponse<BuyerAddressResponse>>
            ) {
                try {
                    if (response.body()?.status!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }

            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<BuyerAddressResponse>>,
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


    fun executeSaveBuyerApi(
        req: AddBuyerRequest,
        responseLiveData: MutableLiveData<ApiResponse<SuccessMsgResponse>>
    ) {

        val call = apiService.saveBuyersList(
            MultipartHelper.getRequestBody(req.userId.toString()),
            MultipartHelper.getRequestBody(req.buyer_type.toString()),
            MultipartHelper.getRequestBody(req.full_name.toString()),
            MultipartHelper.getRequestBody(req.email.toString()),
            MultipartHelper.getRequestBody(req.phone.toString()),
            MultipartHelper.getRequestBody(req.company_name.toString()),
            MultipartHelper.getRequestBody(req.company_email.toString()),
            MultipartHelper.getRequestBody(req.website.toString()),
            MultipartHelper.getRequestBody(req.company_phone.toString()),
            MultipartHelper.getRequestBody(req.beneficiary.toString()),
            MultipartHelper.getRequestBody(req.account_no.toString()),
            MultipartHelper.getRequestBody(req.bank_name.toString()),
            MultipartHelper.getRequestBody(req.ifsc_code.toString()),
            MultipartHelper.getRequestBody(req.pan_no.toString()),
            MultipartHelper.getRequestBody(req.gst_no.toString()),
            MultipartHelper.getRequestBody(req.aadhar_no.toString()),
            MultipartHelper.getMultipartData(req.pan_file_path,"pan_file"),
            MultipartHelper.getMultipartData(req.aadhar_file_path,"aadhar_file"),
            MultipartHelper.getMultipartData(req.gst_file_path,"gst_file"),
            MultipartHelper.getMultipartData(req.profile_image,"profile_image")
        )
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
                    mLog("Buyers Repo onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


}