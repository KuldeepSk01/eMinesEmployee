package com.emines_employee.koin

import com.emines_employee.base.BaseRepository
import com.emines_employee.screen.dashboard.MainRepo
import com.emines_employee.screen.dashboard.buyer.BuyersRepo
import com.emines_employee.screen.dashboard.buyer.buyerorders.BuyersOrderRepo
import com.emines_employee.screen.dashboard.buyer.viewbuyer.addressinfo.BuyersAddressRepo
import com.emines_employee.screen.dashboard.home.attendance.AttendanceRepo
import com.emines_employee.screen.dashboard.buyer.createrequest.selectcategory.CategoryRepo
import com.emines_employee.screen.dashboard.home.HomeRepo
import com.emines_employee.screen.dashboard.home.target.TargetsRepo
import com.emines_employee.screen.dashboard.seller.SellerRepo
import com.emines_employee.screen.login.LoginRepo
import com.emines_employee.screen.otpverification.OtpVerifyRepo
import com.emines_employee.screen.verifyme2.VerifyProfileRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { BaseRepository() }
    single { MainRepo() }
    single { LoginRepo() }
    single { OtpVerifyRepo() }
    single { VerifyProfileRepo() }
    single { AttendanceRepo() }
    single { TargetsRepo() }
    single { HomeRepo() }
    single { BuyersRepo() }
    single { SellerRepo() }
    single { BuyersAddressRepo() }
    single { CategoryRepo() }
    single { BuyersOrderRepo() }
}