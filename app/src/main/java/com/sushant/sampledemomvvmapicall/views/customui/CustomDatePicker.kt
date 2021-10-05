package com.sushant.sampledemomvvmapicall.views.customui


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class CustomDatePicker : TextInputLayout , View.OnClickListener {
    var format = 0
    lateinit var editText: TextInputEditText
    private var dateFormat: String? = null
    private var currentSelectedDate : Long? = null
    val myCalendar = Calendar.getInstance()

    constructor(context: Context) : super(context) {
        super.setOnClickListener(this)
        super.setFocusable(false)
        init(context,null,0)
    }

    constructor(context: Context, format: Int) : super(context) {
        super.setOnClickListener(this)
        super.setFocusable(false)
        this.format = format
        init(context,null,0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DateTimePicker)
        format = a.getInteger(R.styleable.DateTimePicker_format, 0)
        a.recycle()
        init(context,attrs,0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DateTimePicker)
        format = a.getInteger(R.styleable.DateTimePicker_format, 0)
        a.recycle()
        init(context,attrs,defStyleAttr)
    }


    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {
        setWillNotDraw(false)
        editText = TextInputEditText(getContext())
        editText.setOnClickListener(this)
        editText.isFocusable = false
        editText.setSingleLine()
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12F);
        createEditBox(editText)
    }


    private fun createEditBox(editText: TextInputEditText?) {
        addView(editText)
    }

    override fun onClick(v: View) {
        val calendar = Calendar.getInstance()
        val mYear = calendar[Calendar.YEAR]
        val mMonth = calendar[Calendar.MONTH]
        val mDay = calendar[Calendar.DAY_OF_MONTH]
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        when (format) {
            1 -> {
                val datePickerDialog = DatePickerDialog(context, { datepicker, year, month, day ->
                    try {
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, month.plus(1))
                        myCalendar.set(Calendar.DAY_OF_MONTH,day)
                        currentSelectedDate = myCalendar.timeInMillis
                        setText(getDateText(currentSelectedDate))
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }, mYear, mMonth, mDay)
                datePickerDialog.show()
            }
            2 -> {
                val timePickerDialog = TimePickerDialog(context, { view, hourOfDay, minute1 ->
                    try {
                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        myCalendar.set(Calendar.MINUTE, minute1)
                        currentSelectedDate = myCalendar.timeInMillis
                        setText(getDateText(currentSelectedDate))
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }, hour, minute, true)
                timePickerDialog.show()
            }
            else -> {
                val datePickerDialog = DatePickerDialog(context, { datepicker, year, month, day ->
                    val timePickerDialog = TimePickerDialog(context, { view, hourOfDay, minute1 ->
                        try {
                            myCalendar.set(Calendar.YEAR, year)
                            myCalendar.set(Calendar.MONTH, month.plus(1))
                            myCalendar.set(Calendar.DAY_OF_MONTH,day)
                            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            myCalendar.set(Calendar.MINUTE, minute1)
                            currentSelectedDate = myCalendar.timeInMillis
                            setText(getDateText(currentSelectedDate))
                        } catch (e: ParseException) {
                            e.printStackTrace()
                        }
                    }, hour, minute, true)
                    timePickerDialog.show()
                }, mYear, mMonth, mDay)
                datePickerDialog.show()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateText(long: Long?) : String?{
       return  long?.let {
             SimpleDateFormat(getDateFormat()).format(Date(long))?:""
        }
    }

    fun setText(dateString: String?){
        dateString?.let {
            editText.setText(dateString)
        }
    }

    fun setEmptyText(){
        editText.setText("")
    }
    fun setDateFormat(text: String?) {
        dateFormat = text
    }

    private fun getDateFormat(): String = dateFormat ?: Constants.DEFAULT_DATE_TIME_PICKER_FORMAT

    fun setDate(date : Long){
        currentSelectedDate = date
        setText(getDateText(currentSelectedDate))
    }

    fun getDate() : Long? = currentSelectedDate

    fun addTextChangedListener(textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
    }
}