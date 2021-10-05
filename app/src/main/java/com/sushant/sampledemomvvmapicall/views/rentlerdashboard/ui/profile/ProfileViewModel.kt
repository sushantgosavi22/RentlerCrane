package com.sushant.sampledemomvvmapicall.views.rentlerdashboard.ui.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel

class ProfileViewModel  (application: Application) : BaseViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}