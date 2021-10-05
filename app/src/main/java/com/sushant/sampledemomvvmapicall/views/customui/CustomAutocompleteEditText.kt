package com.sushant.sampledemomvvmapicall.views.customui


import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.AdapterView
import android.widget.Filterable
import android.widget.ListAdapter
import com.google.android.material.textfield.TextInputLayout
import com.sushant.sampledemomvvmapicall.R


class CustomAutocompleteEditText : TextInputLayout {
    lateinit var editText: CustomAutoCompleteSpinner

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
        editText = CustomAutoCompleteSpinner(getContext())
        createEditBox(editText)
    }


    private fun createEditBox(editText: CustomAutoCompleteSpinner?) {
        editText?.maxLines = 1
        editText?.isEnabled = this.isEnabled
        editText?.keyListener =null
        editText?.setSingleLine()
        addView(editText)
    }

    fun setText(text: String?) {
        editText.setText(text)
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }

    fun getText(): String = editText.text.toString()

    fun setOnItemClickListener(listener : AdapterView.OnItemClickListener?) {
        editText.onItemClickListener = listener
    }

    fun getDropDownSelectedValue() : String ? = editText.getDropDownSelectedValue()

    fun setDropDownSelectedValue(value: String?) {
        editText.setDropDownSelectedValue(value)
    }

    fun setSelectedPosition(position : Int){
       editText.setSelectedPosition(position)
    }

    fun <T> setAutoCompleteAdapter(adapter: T?) where T : ListAdapter?, T : Filterable? {
        editText.setAdapter(adapter)
    }
}