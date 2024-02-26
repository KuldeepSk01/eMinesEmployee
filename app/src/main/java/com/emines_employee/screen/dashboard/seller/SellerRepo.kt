package com.emines_employee.screen.dashboard.seller

import androidx.lifecycle.MutableLiveData
import com.emines_employee.base.BaseRepository
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.request.AddSellerRequest
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

class SellerRepo : BaseRepository() {
    fun executeSellerListApi(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<BuyersResponse>>>
    ) {
        val call = apiService.sellerList()
        responseLiveData.value = ApiResponse.loading()
        call.enqueue(object : Callback<CollectionBaseResponse<BuyersResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<BuyersResponse>>,
                response: Response<CollectionBaseResponse<BuyersResponse>>
            ) {
                try {
                    if (response.body()?.status!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message)))
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

    fun executeSellerAddressListApi(
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


    fun executeSaveSellerApi(
        req: AddSellerRequest,
        responseLiveData: MutableLiveData<ApiResponse<SuccessMsgResponse>>
    ) {
/*        var panMultipart: MultipartBody.Part? = null
        var aadhaarMultipart: MultipartBody.Part? = null
        var gstMultipart: MultipartBody.Part? = null


        mLog("save buyer request ${req.toString()}")
        val userID = req.userId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val buyerType = req.buyer_type.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val fullName = req.full_name.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val email = req.email.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val phone = req.phone.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val cName = req.company_name.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val cEmail = req.company_email.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val cPhone = req.company_phone.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val cWebsite = req.website.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val beneficiary = req.beneficiary.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val accountNo = req.account_no.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val bankName = req.bank_name.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val ifscCode = req.ifsc_code.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val panNo = req.pan_no.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val aadhaarNo = req.aadhar_no.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val gstNo = req.gst_no.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        if (req.pan_file_path.isNullOrEmpty()) {
            panMultipart = null
        } else {
            val panFile = File(req.pan_file_path!!)
            val panRequestFile: RequestBody =
                panFile.asRequestBody("multipart/form-data".toMediaType())
            panMultipart = MultipartBody.Part.createFormData(
                "pan_file",
                panFile.name,
                panRequestFile
            )
        }
        if (req.aadhar_file_path.isNullOrEmpty()) {
            aadhaarMultipart = null
        } else {
            val aadhaarFile = File(req.pan_file_path!!)
            val aadhaarRequestFile: RequestBody =
                aadhaarFile.asRequestBody("multipart/form-data".toMediaType())
            aadhaarMultipart = MultipartBody.Part.createFormData(
                "aadhar_file",
                aadhaarFile.name,
                aadhaarRequestFile
            )
        }
        if (req.gst_file_path.isNullOrEmpty()) {
            gstMultipart = null
        } else {
            val gstFile = File(req.pan_file_path!!)
            val gstRequestFile: RequestBody =
                gstFile.asRequestBody("multipart/form-data".toMediaType())
            gstMultipart = MultipartBody.Part.createFormData(
                "gst_file",
                gstFile.name,
                gstRequestFile
            )

        }*/

        /*  buyerType,
            fullName,
            email,
            phone,
            cName,
            cEmail,
            cWebsite,
            cPhone,
            beneficiary,
            accountNo,
            bankName,
            ifscCode,
            panNo,
            gstNo,
            aadhaarNo,
            aadhaarMultipart,
            gstMultipart
            profileIMage
            */

        val call = apiService.saveSeller(
            MultipartHelper.getRequestBody(req.userId.toString()),
            MultipartHelper.getRequestBody(req.seller_type.toString()),
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