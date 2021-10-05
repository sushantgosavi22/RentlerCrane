package com.sushant.sampledemomvvmapicall.views.assignmentdetails

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

class AssignmentDetailViewModel(application: Application) : BaseViewModel(application) {

    val databaseHelper = DatabaseHelper(application)
    var repository = VehicleRepository(databaseHelper,application)
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _dataBaseSaveResult = MutableLiveData<DatabaseResult>()
    val dataBaseResult: LiveData<DatabaseResult> = _dataBaseSaveResult

    fun saveAssignment(assignment: Assignment) {
        compositeDisposable.add(
            repository.saveAssignment(assignment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        _dataBaseSaveResult.value = it
                    },
                    onComplete = {
                        _dataBaseSaveResult.value =DatabaseResult.StopLoading
                    })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}