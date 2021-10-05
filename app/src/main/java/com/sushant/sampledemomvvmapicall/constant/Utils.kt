package com.sushant.sampledemomvvmapicall.constant

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.sushant.sampledemomvvmapicall.R
import java.util.Locale


object Utils {

    const val baseURL: String = "https://official-joke-api.appspot.com/"
    const val PERSISTED: String = "persisted"
    const val RESPONSE: String = "response"
    const val EXTRA_PARAM : String = "extra_param"
    const val EXTRA_PARAM_KEY : String = "extra_param_key"
    const val FIRST_PAGE: Int = 1

    fun showToast(context: Context?, message: String?) {
        context?.let {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    fun isInternetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                getNetworkCapabilities(this)
            } ?: false
        } else {
            @Suppress("DEPRECATION")
            connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting ?: false
        }
    }

    /**
     * Get the network capabilities.
     *
     * @param networkCapabilities: Object of [NetworkCapabilities]
     */
    @SuppressLint("NewApi")
    private fun getNetworkCapabilities(networkCapabilities: NetworkCapabilities): Boolean =
        networkCapabilities.run {
            when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            val background =ContextCompat.getDrawable(activity, R.drawable.gradient_theme)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity,android.R.color.transparent)
            window.navigationBarColor = ContextCompat.getColor(activity,android.R.color.transparent)
            window.setBackgroundDrawable(background)
        }
    }

}