package com.emines_employee.network

import com.emines_employee.base.BaseResponse
import com.emines_employee.base.BaseResponse1
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.ApplyLeaveReq
import com.emines_employee.model.request.AttendanceSheetReq
import com.emines_employee.model.request.BuyerAddressRequest
import com.emines_employee.model.request.GetBuyerOrderRequest
import com.emines_employee.model.request.MarkPresentRepo
import com.emines_employee.model.response.Attendance
import com.emines_employee.model.response.AttendanceResponse
import com.emines_employee.model.response.BuyerAddressResponse
import com.emines_employee.model.response.BuyerAddressStateResponse
import com.emines_employee.model.response.BuyerOrderRequest
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.model.response.CategoryResponse
import com.emines_employee.model.response.DashboardResponse
import com.emines_employee.model.response.LoginOtpResponse
import com.emines_employee.model.response.RequestOrderResponse
import com.emines_employee.model.response.TargetsResponse
import com.emines_employee.model.response.UserResponse
import com.emines_employee.util.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST(Constants.UrlsEndPoint.login)
    fun login(@Query("mobile") mobile:String):Call<LoginOtpResponse>

    @POST(Constants.UrlsEndPoint.verifyOtp)
    fun verifyOtp(@Query("mobile") mobile:String,@Query("otp") otp:String):Call<BaseResponse<UserResponse>>

    @POST(Constants.UrlsEndPoint.addEmployeeAttendance)
    fun addEmployeeAttendance(@Body req: MarkPresentRepo):Call<SuccessMsgResponse>

    @POST(Constants.UrlsEndPoint.addEmployeeLeave)
    fun applyLeave(@Body req: ApplyLeaveReq):Call<SuccessMsgResponse>

    @POST(Constants.UrlsEndPoint.attendanceSheet)
    fun attendanceSheet(@Body req: AttendanceSheetReq):Call<AttendanceResponse<Attendance>>

    @POST(Constants.UrlsEndPoint.employeeProfile)
    fun employeeProfile(@Query("user_id") userId:Int):Call<BaseResponse<UserResponse>>

    @POST(Constants.UrlsEndPoint.employeeTarget)
    fun employeeTargets(@Query("user_id") userId:Int):Call<BaseResponse<TargetsResponse>>

    @POST(Constants.UrlsEndPoint.buyerLists)
    fun buyersList(): Call<CollectionBaseResponse<BuyersResponse>>

    @POST(Constants.UrlsEndPoint.buyerAddresses)
    fun buyersAddressList(@Query("buyer_id") buyerId: Int): Call<CollectionBaseResponse<BuyerAddressResponse>>

    @POST(Constants.UrlsEndPoint.state_list)
    fun buyersAddressStateList(@Query("buyer_id") buyerId: Int): Call<CollectionBaseResponse<BuyerAddressStateResponse>>

    @POST(Constants.UrlsEndPoint.addBuyerAddress)
    fun addBuyersAddress(@Body req: BuyerAddressRequest): Call<SuccessMsgResponse>

    @Multipart
    @POST(Constants.UrlsEndPoint.saveBuyer)
    fun saveBuyersList(
        @Part("user_id") userId: RequestBody,
        @Part("buyer_type") buyerType: RequestBody,
        @Part("full_name") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("company_name") companyName: RequestBody,
        @Part("company_email") companyEmail: RequestBody,
        @Part("website") website: RequestBody,
        @Part("company_phone") companyPhone: RequestBody,
        @Part("beneficiary") beneficiary: RequestBody,
        @Part("account_no") accountNo: RequestBody,
        @Part("bank_name") bankName: RequestBody,
        @Part("ifsc_code") ifscCode: RequestBody,
        @Part("pan_no") pan_no: RequestBody,
        @Part("gst_no") gst_no: RequestBody,
        @Part("aadhar_no") aadharNumber: RequestBody,
        @Part panFile: MultipartBody.Part?,
        @Part aadharFile: MultipartBody.Part?,
        @Part gstFile: MultipartBody.Part?,
        @Part profileImage: MultipartBody.Part?,
        ): Call<SuccessMsgResponse>


    @GET(Constants.UrlsEndPoint.getCategory)
    fun getCategoryList(): Call<CollectionBaseResponse<CategoryResponse>>

    @POST(Constants.UrlsEndPoint.buyerOrderRequest)
    fun buyerOrderRequest(@Body buyerOrderRequest: BuyerOrderRequest): Call<SuccessMsgResponse>


    @POST(Constants.UrlsEndPoint.requestBuyerOrderList)
    fun getBuyerOrdersList(@Body request: GetBuyerOrderRequest): Call<CollectionBaseResponse<RequestOrderResponse>>

    @FormUrlEncoded
    @POST(Constants.UrlsEndPoint.employeeDashboard)
    fun dashboardData(@Field("user_id") userId:Int): Call<BaseResponse1<DashboardResponse>>


    @Multipart
    @POST(Constants.UrlsEndPoint.saveSeller)
    fun saveSeller(
        @Part("user_id") userId: RequestBody,
        @Part("seller_type") sellerType: RequestBody,
        @Part("full_name") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("company_name") companyName: RequestBody,
        @Part("company_email") companyEmail: RequestBody,
        @Part("website") website: RequestBody,
        @Part("company_phone") companyPhone: RequestBody,
        @Part("beneficiary") beneficiary: RequestBody,
        @Part("account_no") accountNo: RequestBody,
        @Part("bank_name") bankName: RequestBody,
        @Part("ifsc_code") ifscCode: RequestBody,
        @Part("pan_no") pan_no: RequestBody,
        @Part("gst_no") gst_no: RequestBody,
        @Part("aadhar_no") aadharNumber: RequestBody,
        @Part panFile: MultipartBody.Part?,
        @Part aadharFile: MultipartBody.Part?,
        @Part gstFile: MultipartBody.Part?,
        @Part profileImage: MultipartBody.Part?,
    ): Call<SuccessMsgResponse>


    @POST(Constants.UrlsEndPoint.sellerLists)
    fun sellerList(): Call<CollectionBaseResponse<BuyersResponse>>



}
