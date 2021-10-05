package com.sushant.sampledemomvvmapicall.views.vehicaldetail

import com.sushant.sampledemomvvmapicall.database.firebase.BaseFirebaseModel
import com.sushant.sampledemomvvmapicall.views.adapter.AdapterItem

class Vehicle(
    var name : String?="",
    var number : String?="",
    var imageUrl : String?="",
    var modelName : String?=""
) : BaseFirebaseModel(), AdapterItem {

    fun clear(){
        name =""
        number =""
        imageUrl =""
        modelName =""
        firebaseId =-1
    }
}