package com.sushant.sampledemomvvmapicall.repositorys.vehiclerepo

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseHelper
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.database.firebase.FirebaseHelper
import com.sushant.sampledemomvvmapicall.views.assignmentdetails.Assignment
import com.sushant.sampledemomvvmapicall.views.vehicaldetail.Vehicle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class VehicleRepository(private val databaseHelper: DatabaseHelper, val context: Context) : IVehicleRepository {

    override fun saveVehicle(vehicle: Vehicle): Observable<DatabaseResult> {
        return databaseHelper.saveVehicle(vehicle)
    }

    override fun fetchVehicle(): Observable<DatabaseResult> {
        return databaseHelper.fetchVehicle()
    }

    override fun saveAssignment(assignment: Assignment): Observable<DatabaseResult> {
        return databaseHelper.saveAssignment(assignment)
    }

    override fun fetchAssignment(vehicleId: Long): Observable<DatabaseResult> {
        return databaseHelper.fetchAssignment(vehicleId)
    }

}