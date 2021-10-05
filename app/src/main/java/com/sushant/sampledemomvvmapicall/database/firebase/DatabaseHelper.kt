package com.sushant.sampledemomvvmapicall.database.firebase

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import com.sushant.sampledemomvvmapicall.views.assignmentdetails.Assignment
import com.sushant.sampledemomvvmapicall.views.vehicaldetail.Vehicle
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

open class DatabaseHelper(val context: Context) {

    private fun getUserName() : String{
        return "pravin"
    }

    private fun getVehiclePath() : String = getUserName().plus("/").plus(VEHICLE)
    private fun getAssignmentPath(vehicleId: Long) : String = getVehiclePath().plus("/").plus(vehicleId).plus("/").plus(ASSIGNMENT)

    fun saveVehicle(vehicle: Vehicle): Observable<DatabaseResult> {
        return BehaviorSubject.create<DatabaseResult> {
            it.onNext(DatabaseResult.StartLoading)
            if(vehicle.firebaseId==-1L){
                FirebaseHelper.getInstance(context).pushById(getVehiclePath(), vehicle, object : FirebaseHelper.PushListener {
                    override fun onPush(path: String?, ref: DatabaseReference?) {
                        if (ref != null) {
                            it.onNext(DatabaseResult.Success("vehicle saved successfully."))
                        } else {
                            it.onNext(DatabaseResult.Failure(Throwable("vehicle not saved")))
                        }
                        it.onComplete()
                    }
                }).setErrorListener(object : FirebaseHelper.ErrorListener {
                    override fun onError(error: DatabaseError) {
                        it.onNext(DatabaseResult.Failure(Throwable(error.message)))
                        it.onComplete()
                    }
                })
            }else{
                FirebaseHelper.getInstance(context).set(getVehiclePath().plus("/").plus(vehicle.firebaseId), vehicle, object : FirebaseHelper.SetListener{
                    override fun onSet(path: String?) {
                        if (path != null) {
                            it.onNext(DatabaseResult.Success("Assignment saved successfully."))
                        }else{
                            it.onNext(DatabaseResult.Failure(Throwable("Assignment not saved")))
                        }
                        it.onComplete()
                    }

                }).setErrorListener(object : FirebaseHelper.ErrorListener {
                    override fun onError(error: DatabaseError) {
                        it.onNext(DatabaseResult.Failure(Throwable(error.message)))
                        it.onComplete()
                    }
                })
            }
        }
    }


    fun saveAssignment(assignment: Assignment): Observable<DatabaseResult> {
        return BehaviorSubject.create<DatabaseResult> {
            it.onNext(DatabaseResult.StartLoading)
            if(assignment.firebaseId==-1L){
                FirebaseHelper.getInstance(context).pushById(getAssignmentPath(assignment.vehicleId), assignment, object : FirebaseHelper.PushListener {
                    override fun onPush(path: String?, ref: DatabaseReference?) {
                        if (ref != null) {
                            it.onNext(DatabaseResult.Success("Assignment saved successfully."))
                        } else {
                            it.onNext(DatabaseResult.Failure(Throwable("Assignment not saved")))
                        }
                        it.onComplete()
                    }
                }).setErrorListener(object : FirebaseHelper.ErrorListener {
                    override fun onError(error: DatabaseError) {
                        it.onNext(DatabaseResult.Failure(Throwable(error.message)))
                        it.onComplete()
                    }
                })
            }else{
                FirebaseHelper.getInstance(context).set(getAssignmentPath(assignment.vehicleId).plus("/").plus(assignment.firebaseId), assignment, object : FirebaseHelper.SetListener{
                    override fun onSet(path: String?) {
                        if (path != null) {
                            it.onNext(DatabaseResult.Success("Assignment saved successfully."))
                        }else{
                            it.onNext(DatabaseResult.Failure(Throwable("Assignment not saved")))
                        }
                        it.onComplete()
                    }

                }).setErrorListener(object : FirebaseHelper.ErrorListener {
                    override fun onError(error: DatabaseError) {
                        it.onNext(DatabaseResult.Failure(Throwable(error.message)))
                        it.onComplete()
                    }
                })
            }
        }
    }

    fun fetchVehicle(): Observable<DatabaseResult> {
        return BehaviorSubject.create<DatabaseResult> { emitter->
            emitter.onNext(DatabaseResult.StartLoading)
            FirebaseHelper.getInstance(context).get(getVehiclePath(),object : FirebaseHelper.GetListener{
                override fun onGet(path: String?, data: DataSnapshot?) {
                    data?.let {
                        val listData: ArrayList<Vehicle> = getListData(data, Vehicle::class.java)
                        if (!listData.isNullOrEmpty()) {
                            emitter.onNext(DatabaseResult.Success(listData))
                        }else{
                            emitter.onNext(DatabaseResult.Failure(Throwable("Vehicle not found. Please add vehicle first.")))
                        }
                    }
                    emitter.onComplete()
                }
            }).setErrorListener(object :FirebaseHelper.ErrorListener{
                override fun onError(error: DatabaseError) {
                  emitter.onNext(DatabaseResult.Failure(error.toException()))
                  emitter.onComplete()
                }
            })
        }
    }

    fun fetchAssignment(vehicleId: Long): Observable<DatabaseResult> {
        return BehaviorSubject.create<DatabaseResult> { emitter->
            emitter.onNext(DatabaseResult.StartLoading)
            val mQuery : Query = if(vehicleId==-1L){
                 FirebaseHelper.getInstance(context).query(getVehiclePath())
            }else{
                FirebaseHelper.getInstance(context).query(getAssignmentPath(vehicleId))
            }

            FirebaseHelper.getInstance(context).get(mQuery,object : FirebaseHelper.GetListener{
                override fun onGet(path: String?, data: DataSnapshot?) {
                    data?.let {

                        var assignment: ArrayList<Assignment> = arrayListOf<Assignment>()
                        if(vehicleId==-1L){
                            if (null != data.value) {
                                for (children in data.children) {
                                    try {
                                        for (subChildren in children.child(ASSIGNMENT).children) {
                                            try {
                                                val model = subChildren.getValue(Assignment::class.java)
                                                if (null != model) {
                                                    assignment.add(model)
                                                }
                                            }catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }else{
                            assignment= getListData(data, Assignment::class.java)
                        }
                        if (!assignment.isNullOrEmpty()) {
                            emitter.onNext(DatabaseResult.Success(assignment))
                        }else{
                            emitter.onNext(DatabaseResult.Failure(Throwable("Assignment not found. Please add vehicle first.")))
                        }
                    }
                    emitter.onComplete()
                }
            }).setErrorListener(object :FirebaseHelper.ErrorListener{
                override fun onError(error: DatabaseError) {
                    emitter.onNext(DatabaseResult.Failure(error.toException()))
                    emitter.onComplete()
                }
            })
        }
    }

    private fun <T> getListData(dataSnapshot: DataSnapshot, valueType: Class<T>): ArrayList<T> {
        val list = ArrayList<T>()
        if (null != dataSnapshot.value) {
            for (children in dataSnapshot.children) {
                try {

                    val model = children.getValue(valueType)
                    if (null != model) {
                        list.add(model)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return list
    }

   private fun <T> getClassData(dataSnapshot: DataSnapshot, valueType: Class<T>): T? {
        var data: T? = null
        try {
            if (null != dataSnapshot.value) {
                val nullableData = dataSnapshot.getValue(valueType)
                nullableData?.apply {
                    data = this
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }




    companion object {
        const val VEHICLE = "vehicle"
        const val ASSIGNMENT = "assignment"
    }
}