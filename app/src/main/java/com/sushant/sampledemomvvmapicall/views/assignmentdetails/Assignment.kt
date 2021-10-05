package com.sushant.sampledemomvvmapicall.views.assignmentdetails

import android.app.Application
import android.content.res.Resources
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.application.App
import com.sushant.sampledemomvvmapicall.database.firebase.BaseFirebaseModel
import com.sushant.sampledemomvvmapicall.views.adapter.AdapterItem

class Assignment(
    var vehicleId: Long = 0L,
    var hour: Int = 0,
    var perHourAmount: Int = 0,
    var startDate: Long = 0L,
    var endDate: Long = 0L,
    var ownerName: String? = "",
    var mobileNumber: String? = "",
    var loaction: String? = "",
    var dieselInLiter: String? = "",
    var paymentTerm: String? = getDefaultPaymentTerm(),
    var paymentAmount: Int? = 0,
    var paidAmount: Int? = 0,
    var remainingPaymentAmount: Int? = 0
) : BaseFirebaseModel(), AdapterItem {

    var vehicleImageUrl :String?= ""

    fun clear() {
        hour = 0
        startDate = 0L
        endDate = 0L
        ownerName = ""
        mobileNumber = ""
        loaction = ""
        dieselInLiter = ""
        paymentAmount = 0
        paidAmount = 0
        perHourAmount = 0
        remainingPaymentAmount = 0
        paymentTerm = getDefaultPaymentTerm()
        firebaseId = -1L
    }

    companion object {

        fun getDefaultPaymentTerm(): String? {
            val array = App.appContext?.resources?.getStringArray(R.array.payment_terms_list)
            return  array?.get(0)
        }
    }
}



