package com.sushant.sampledemomvvmapicall.database.firebase

sealed class DatabaseResult {
    object StartLoading : DatabaseResult()
    data class Success(var data : Any?) : DatabaseResult()
    data class Failure(var exception: Throwable) : DatabaseResult()
    object StopLoading : DatabaseResult()
}