package com.sushant.sampledemomvvmapicall.views.customui

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.Button
import com.google.android.material.button.MaterialButton
import com.sushant.sampledemomvvmapicall.R

class CustomFullLengthButton : MaterialButton {
    constructor(context: Context?) : super(ContextThemeWrapper(context, R.style.CustomFullLengthButtonStyle)) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(ContextThemeWrapper(context, R.style.CustomFullLengthButtonStyle), attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(ContextThemeWrapper(context, R.style.CustomFullLengthButtonStyle), attrs, defStyleAttr) {
    }
}