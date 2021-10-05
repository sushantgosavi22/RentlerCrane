package com.sushant.sampledemomvvmapicall.views.rentlerdashboard.ui.assignment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseHelper
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.IFeedRepository
import com.sushant.sampledemomvvmapicall.repositorys.vehiclerepo.VehicleRepository
import com.sushant.sampledemomvvmapicall.views.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AssignmentViewModel (application: Application) : BaseViewModel(application) {

    val databaseHelper = DatabaseHelper(application)
    var repository = VehicleRepository(databaseHelper, application)
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _dataBaseFetchAssignmentResult = MutableLiveData<DatabaseResult>()
    val dataBaseFetchAssignmentResult: LiveData<DatabaseResult> = _dataBaseFetchAssignmentResult


    fun fetchAssignment(vehicleId : Long = 0L) {
        compositeDisposable.add(
            repository.fetchAssignment(vehicleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        _dataBaseFetchAssignmentResult.value = it
                    },
                    onComplete = {
                        _dataBaseFetchAssignmentResult.value = DatabaseResult.StopLoading
                    })
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}