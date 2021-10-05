package com.sushant.sampledemomvvmapicall.views.rentlerdashboard.ui.vehicle

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseHelper
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.repositorys.vehiclerepo.VehicleRepository
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class VehicleViewModel(application: Application) : BaseViewModel(application) {

    val databaseHelper = DatabaseHelper(application)
    var repository = VehicleRepository(databaseHelper, application)
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _dataBaseFetchVehicleResult = MutableLiveData<DatabaseResult>()
    val dataBaseFetchVehicleResult: LiveData<DatabaseResult> = _dataBaseFetchVehicleResult


    fun fetchVehicle() {
        compositeDisposable.add(
            repository.fetchVehicle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        _dataBaseFetchVehicleResult.value = it
                    },
                    onComplete = {
                        _dataBaseFetchVehicleResult.value = DatabaseResult.StopLoading
                    })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}