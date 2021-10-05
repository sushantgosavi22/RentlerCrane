package com.sushant.sampledemomvvmapicall.views.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setStatusBarGradiant(this)
    }
    private var dialog: AlertDialog? = null
    /**
     * Show progress bar.
     */
    fun showProgressBar(){
        if (dialog == null)
            dialog = AlertDialog.Builder(this).setCancelable(false).setView(R.layout.progress_dailog_layout).create()
        dialog?.show()
    }

    /**
     * Hide progress bar.
     */
    fun hideProgressBar(){
        if (dialog != null && dialog?.isShowing==true)
            dialog?.hide()
    }
}