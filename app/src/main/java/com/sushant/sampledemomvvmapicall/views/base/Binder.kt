package com.sushant.sampledemomvvmapicall.views.base

import android.annotation.SuppressLint
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.views.customui.CustomAutocompleteEditText
import com.sushant.sampledemomvvmapicall.views.customui.CustomDatePicker
import com.sushant.sampledemomvvmapicall.views.customui.CustomTextInputEditText
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat


object Binder {

    @SuppressLint("CheckResult")
    @BindingAdapter("bind:imageUrl")
    @JvmStatic
    public fun bindImageUrl(view: ImageView, url: String?) {
        if(!url.isNullOrEmpty() && url.contains("file")){
            Glide.with(view)
                .load(Uri.parse(url))
                .placeholder(R.drawable.jcb_logo)
                .error(R.drawable.jcb_logo)
                .into(view)
        }else{
            view.setImageResource(R.drawable.jcb_logo)
        }
    }

    @BindingAdapter("bind:type")
    @JvmStatic
    public fun bindType(view: TextView, type: String?) {
        view.text = type?.capitalize()?:""
    }


    @BindingAdapter("bind:digit")
    @JvmStatic
    public fun bindDigit(view: CustomTextInputEditText, digit: String?) {
        digit?.let {
            view.setDigit(digit)
        }
    }


    @BindingAdapter("bind:maxLength")
    @JvmStatic
    public fun bindMaxLength(view: CustomTextInputEditText, maxLength: String?) {
        maxLength?.let {
            view.setMaxLength(maxLength.toInt())
        }
    }


    /* Int value */
    @BindingAdapter("bind:intValue")
    @JvmStatic
    public fun bindIntValue(view: CustomTextInputEditText, textValue: Int?) {
        textValue?.let {
            if(textValue> 0){
                view.setText(textValue.toString())
            }else{
                view.setText("")
            }
        }

    }

    @InverseBindingAdapter(attribute = "bind:intValue")
    @JvmStatic
    public fun getBindIntValue(view: CustomTextInputEditText): Int {
        return try{
            view.getText().toInt()
        }catch (e : Exception){
            e.printStackTrace()
            0
        }
    }

    @BindingAdapter(value = ["bind:intValueAttrChanged"])
    @JvmStatic
    fun setIntListener(editText: CustomTextInputEditText, listener: InverseBindingListener?) {
        if (listener != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    listener.onChange()
                }
            })
        }
    }

    /* Long value */



    /* Int value */
    @BindingAdapter("bind:longDateValue", "bind:dateFormat", "bind:dateFormatMode",  requireAll = false)
    @JvmStatic
    public fun bindLongDateValue(view: CustomDatePicker, textValue: Long?, dateFormat: String?,dateFormatMode : Int? ) {
        view.setDateFormat(dateFormat)
        textValue?.let {
            if(textValue > 0){
                view.setDate(textValue)
            }else{
                view.setEmptyText()
            }
        }
    }

    @InverseBindingAdapter(attribute = "bind:longDateValue")
    @JvmStatic
    public fun getBindLongDateValue(view: CustomDatePicker): Long {
        return  view.getDate()?:0
    }

    @BindingAdapter(value = ["bind:longDateValueAttrChanged"])
    @JvmStatic
    fun setLongDateValueListener(editText: CustomDatePicker, listener: InverseBindingListener?) {
        if (listener != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    listener.onChange()
                }
            })
        }
    }

    /* Long value */

    /* String value */
    @BindingAdapter("bind:textValue")
    @JvmStatic
    public fun bindTextValue(view: CustomTextInputEditText, textValue: String?) {
        view.setText(textValue)
    }

    @InverseBindingAdapter(attribute = "bind:textValue")
    @JvmStatic
    public fun getBindTextValue(view: CustomTextInputEditText): String {
        return  view.getText()
    }

    @BindingAdapter(value = ["bind:textValueAttrChanged"])
    @JvmStatic
    fun setListener(editText: CustomTextInputEditText, listener: InverseBindingListener?) {
        if (listener != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    listener.onChange()
                }
            })
        }
    }
    /* String value */


    /* Capitalize String value */
    @BindingAdapter("bind:textCapitalizeValue")
    @JvmStatic
    public fun bindCapitalizeTextValue(view: CustomTextInputEditText, textValue: String?) {
        view.setText(textValue?.capitalize())
    }

    @InverseBindingAdapter(attribute = "bind:textCapitalizeValue")
    @JvmStatic
    public fun getCapitalizeBindTextValue(view: CustomTextInputEditText): String {
        return  view.getText().capitalize()
    }

    @BindingAdapter(value = ["bind:textCapitalizeValueAttrChanged"])
    @JvmStatic
    fun setCapitalizeListener(editText: CustomTextInputEditText, listener: InverseBindingListener?) {
        if (listener != null) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    listener.onChange()
                }
            })
        }
    }
    /* Capitalize String value */




    /* AutoComplete value */
    @BindingAdapter(value = ["bind:autocompleteValueAttrChanged"])
    @JvmStatic
    fun setValueListener(editText: CustomAutocompleteEditText, listener: InverseBindingListener?) {
        if (listener != null) {
            editText.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                editText.setSelectedPosition(position)
                listener.onChange()
            })
        }
    }

    @BindingAdapter("bind:autocompleteValue")
    @JvmStatic
    public fun bindSelectedValue(view: CustomAutocompleteEditText, textValue: String?) {
        view.setDropDownSelectedValue(textValue)
    }

    @InverseBindingAdapter(attribute = "bind:autocompleteValue")
    @JvmStatic
    public fun getBindSelectedValue(view: CustomAutocompleteEditText): String? {
        return view.getDropDownSelectedValue()
    }

    @JvmStatic
    @BindingAdapter("bind:entries", "bind:itemLayout", "bind:textViewId", requireAll = false)
    fun CustomAutocompleteEditText.bindAdapter(entries: Array<String>, @LayoutRes itemLayout: Int?, @IdRes textViewId: Int?) {
        val adapter = when {
            itemLayout == null -> {
                ArrayAdapter(context, android.R.layout.simple_list_item_1, android.R.id.text1, entries)
            }
            textViewId == null -> {
                ArrayAdapter(context, itemLayout, entries)
            }
            else -> {
                ArrayAdapter(context, itemLayout, textViewId, entries)
            }
        }
        setAutoCompleteAdapter(adapter)
    }
    /* AutoComplete value */
}