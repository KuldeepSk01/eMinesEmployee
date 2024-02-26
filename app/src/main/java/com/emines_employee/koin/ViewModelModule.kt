package com.emines_employee.koin

import com.emines_employee.base.BaseViewModel
import com.emines_employee.screen.dashboard.MainViewModel
import com.emines_employee.screen.dashboard.buyer.BuyersViewModel
import com.emines_employee.screen.dashboard.buyer.buyerorders.BuyerOrderViewModel
import com.emines_employee.screen.dashboard.buyer.viewbuyer.addressinfo.BuyersAddressViewModel
import com.emines_employee.screen.dashboard.home.HomeViewModel
import com.emines_employee.screen.dashboard.home.attendance.AttendanceViewModel
import com.emines_employee.screen.dashboard.buyer.createrequest.selectcategory.CategoryViewModel
import com.emines_employee.screen.dashboard.home.target.TargetsViewModel
import com.emines_employee.screen.dashboard.seller.SellerViewModel
import com.emines_employee.screen.login.LoginViewModel
import com.emines_employee.screen.onboarding.OnBoardViewModel
import com.emines_employee.screen.otpverification.OTPVerifyViewModel
import com.emines_employee.screen.verifyme.VerifyMeViewModel
import com.emines_employee.screen.verifyme2.ApplyLeaveViewModel
import com.emines_employee.screen.verifyme2.VerifyMe2ViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { BaseViewModel() }
    single { MainViewModel(get()) }
    single { LoginViewModel(get()) }
    single { OnBoardViewModel() }
    single { OTPVerifyViewModel(get()) }

    single { VerifyMeViewModel() }
    single { VerifyMe2ViewModel(get()) }
    single { HomeViewModel(get()) }

    single { ApplyLeaveViewModel(get()) }
    single { AttendanceViewModel(get()) }
    single { TargetsViewModel(get()) }
    single { BuyersViewModel(get()) }
    single { SellerViewModel(get()) }
    single { BuyersAddressViewModel(get()) }
    single { CategoryViewModel(get()) }
    single { BuyerOrderViewModel(get()) }
}