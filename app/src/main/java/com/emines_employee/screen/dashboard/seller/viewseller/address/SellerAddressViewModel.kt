package com.emines_employee.screen.dashboard.seller.viewseller.address

import androidx.lifecycle.MutableLiveData
import com.emines_employee.R
import com.emines_employee.base.BaseViewModel
import com.emines_employee.base.CollectionBaseResponse
import com.emines_employee.base.SuccessMsgResponse
import com.emines_employee.model.request.AddBuyerRequest
import com.emines_employee.model.request.BuyerAddressRequest
import com.emines_employee.model.request.SellerAddressRequest
import com.emines_employee.model.response.BuyerAddressResponse
import com.emines_employee.model.response.BuyerAddressStateResponse
import com.emines_employee.model.response.BuyersResponse
import com.emines_employee.network.ApiResponse
import com.emines_employee.util.validation.ValidationResult
import com.emines_employee.util.validation.ValidationState

class SellerAddressViewModel(private val repo: SellerAddressRepo) : BaseViewModel() {

    private val buyersStateListResponse =
        MutableLiveData<ApiResponse<CollectionBaseResponse<BuyerAddressStateResponse>>>()

    private val addBuyerAddressResponse =
        MutableLiveData<ApiResponse<SuccessMsgResponse>>()


    fun hitStateListApi() {
        repo.executeStateListApi(buyersStateListResponse)
    }

    fun getStateListResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<BuyerAddressStateResponse>>> {
        return buyersStateListResponse
    }


    fun hitAddSellerAddressApi(req: SellerAddressRequest) {
        repo.executeAddSellerAddressApi(req, addBuyerAddressResponse)
    }

    fun getAddSellerAddressResponse(): MutableLiveData<ApiResponse<SuccessMsgResponse>> {
        return addBuyerAddressResponse
    }

    /*
        var validationResponseObserver = MutableLiveData<ValidationState>()
        fun isValidData(buyerType: String?, fullName: String?, email: String?, phone: String?) {
            if (buyerType?.trim()
                    ?.let { validator.validBuyerType(buyerType) } != ValidationResult.SUCCESS
            ) {
                if (buyerType?.trim()
                        ?.let { validator.validBuyerType(buyerType) } == ValidationResult.EMPTY_BUYER_TYPE
                ) {
                    validationResponseObserver.postValue(
                        ValidationState(
                            ValidationResult.EMPTY_BUYER_TYPE,
                            R.string.error_buyer_type_empty
                        )
                    )
                    return
                }
            }
            if (fullName?.trim()
                    ?.let { validator.validFullName(fullName) } != ValidationResult.SUCCESS
            ) {
                if (fullName?.trim()
                        ?.let { validator.validFullName(fullName) } == ValidationResult.EMPTY_FULL_NAME
                ) {
                    validationResponseObserver.postValue(
                        ValidationState(
                            ValidationResult.EMPTY_FULL_NAME,
                            R.string.error_full_name_empty
                        )
                    )
                    return
                }
            }
            if (email?.trim()?.let { validator.validEmail(email) } != ValidationResult.SUCCESS) {
                if (email?.trim()
                        ?.let { validator.validEmail(email) } == ValidationResult.EMPTY_EMAIL
                ) {
                    validationResponseObserver.postValue(
                        ValidationState(
                            ValidationResult.EMPTY_EMAIL,
                            R.string.error_email_empty
                        )
                    )
                    return
                }
                if (email?.trim()
                        ?.let { validator.validEmail(email) } == ValidationResult.INVALID_EMAIL
                ) {
                    validationResponseObserver.postValue(
                        ValidationState(
                            ValidationResult.INVALID_EMAIL,
                            R.string.error_valid_email
                        )
                    )
                    return
                }
            }
            if (phone?.trim()
                    ?.let { validator.validMobileNo(phone) } != ValidationResult.SUCCESS
            ) {
                if (phone?.trim()
                        ?.let { validator.validMobileNo(phone) } == ValidationResult.EMPTY_MOBILE_NUMBER
                ) {
                    validationResponseObserver.postValue(
                        ValidationState(
                            ValidationResult.EMPTY_MOBILE_NUMBER,
                            R.string.error_mobile_empty
                        )
                    )
                    return
                }
                if (phone?.trim()
                        ?.let { validator.validMobileNo(phone) } == ValidationResult.VALID_MOBILE_NUMBER
                ) {
                    validationResponseObserver.postValue(
                        ValidationState(
                            ValidationResult.VALID_MOBILE_NUMBER,
                            R.string.error_mobile_length
                        )
                    )
                    return
                }
            }
            validationResponseObserver.postValue(
                ValidationState(
                    ValidationResult.SUCCESS,
                    R.string.verify_success
                )
            )
        }
    */


}