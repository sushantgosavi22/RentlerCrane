package com.sushant.sampledemomvvmapicall.repositorys.vehiclerepo

import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.views.assignmentdetails.Assignment
import com.sushant.sampledemomvvmapicall.views.vehicaldetail.Vehicle
import io.reactivex.Observable

interface IVehicleRepository {
    fun saveVehicle(vehicle: Vehicle) : Observable<DatabaseResult>
    fun fetchVehicle() : Observable<DatabaseResult>
    fun saveAssignment(assignment: Assignment): Observable<DatabaseResult>
    fun fetchAssignment(vehicleId: Long): Observable<DatabaseResult>
}