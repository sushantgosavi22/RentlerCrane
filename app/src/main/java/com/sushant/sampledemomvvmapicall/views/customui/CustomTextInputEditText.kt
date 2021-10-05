package com.sushant.sampledemomvvmapicall.views.customui


import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sushant.sampledemomvvmapicall.R


class CustomTextInputEditText : TextInputLayout {
    lateinit var editText: TextInputEditText

    constructor(context: Context?) : super(ContextThemeWrapper(context, R.style.MyCustomTextInputStyle)) {
        init(context, null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(ContextThemeWrapper(context, R.style.MyCustomTextInputStyle), attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(ContextThemeWrapper(context, R.style.MyCustomTextInputStyle), attrs,
                                                                                    defStyleAttr) {
        init(context, attrs, 0)
    }

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        setWillNotDraw(false)
        editText = TextInputEditText(getContext())
        createEditBox(editText)
    }


    private fun createEditBox(editText: TextInputEditText?) {
        editText?.maxLines = 1
        editText?.isEnabled = this.isEnabled
        editText?.setSingleLine()
        addView(editText)
    }

    fun setDigit(digit: String) {
        editText.keyListener = DigitsKeyListener.getInstance(digit);
    }

    fun setMaxLength(maxLength: Int) {
        editText.filters += InputFilter.LengthFilter(maxLength)
    }

    fun setText(text: String?) {
        editText.setText(text)
    }

    fun getText(): String = editText.text.toString()
    fun addTextChangedListener(textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }

}