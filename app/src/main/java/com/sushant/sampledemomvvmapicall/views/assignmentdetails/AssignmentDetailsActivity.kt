package com.sushant.sampledemomvvmapicall.views.assignmentdetails

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.databinding.ActivityAssignmentDetailsBinding
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import java.lang.Exception

class AssignmentDetailsActivity : BaseActivity() {

    private lateinit var assignmentDetailViewModel: AssignmentDetailViewModel
    private lateinit var binding: ActivityAssignmentDetailsBinding
    private val assignment by lazy {
        val extra = intent.getSerializableExtra(Utils.EXTRA_PARAM)
        if (extra != null) extra as Assignment else Assignment(vehicleId = vehicleId).apply { firebaseId =-1L }
    }

    private val vehicleId by lazy {
        intent.getLongExtra(Utils.EXTRA_PARAM_KEY,-1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_assignment_details)
        binding.assignment = assignment
        setContentView(binding.root)
        assignmentDetailViewModel = ViewModelProvider(this).get(AssignmentDetailViewModel::class.java)
        setUpView()
    }

    private fun setUpView() {

        assignmentDetailViewModel.dataBaseResult.observe(this, Observer { databaseSaveResult ->
            var message: String? = ""
            when (databaseSaveResult) {
                is DatabaseResult.Success -> {
                    message = databaseSaveResult.data as String
                    assignment.clear()
                    binding.assignment = assignment
                    Utils.showToast(this@AssignmentDetailsActivity, message)
                }
                is DatabaseResult.Failure -> {
                    message = databaseSaveResult.exception.message
                    Utils.showToast(this@AssignmentDetailsActivity, message)
                }
                is DatabaseResult.StartLoading -> {
                    showProgressBar()
                }
                is DatabaseResult.StopLoading-> {
                    hideProgressBar()
                }
            }
        })

        binding.btnSaveAssignment.setOnClickListener {
            if (validate()) {
                assignmentDetailViewModel.saveAssignment(assignment)
            }
        }

        binding.includedToolbar.tvAppbarTitle.text = getString(R.string.assignment_detail)

        binding.hour.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updatePaymentAmount()
            }
        })
        binding.perHourAmount.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updatePaymentAmount()
            }
        })

        binding.paidAmount.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateRemainingAmount()
            }
        })
    }

    private fun updatePaymentAmount(){
       val payment = try {
           val hour = binding.hour.getText().toInt()
           val rupeesPerHour = binding.perHourAmount.getText().toInt()
           val payment= hour*rupeesPerHour
           payment.toString()
        }catch (exception : Exception){
            exception.printStackTrace()
           ""
        }
        binding.paymentAmount.setText(payment)
    }
    private fun updateRemainingAmount(){
        val remainingAmount = try {
            val paid = binding.paidAmount.getText().toInt()
            val payment = binding.paymentAmount.getText().toInt()
            val remaining = payment-paid
            if(remaining>0){
                remaining.toString()
            }else{
                ""
            }
        }catch (exception : Exception){
            exception.printStackTrace()
            ""
        }
        binding.remainingAmount.setText(remainingAmount)
    }

    private fun validate(): Boolean {
        return if (assignment.ownerName?.isNotEmpty() == true &&
            assignment.dieselInLiter?.isNotEmpty() == true &&
            assignment.hour != 0
        ) {
            true;
        } else {
            Utils.showToast(this@AssignmentDetailsActivity, "Please enter all details first.")
            false
        }
    }
}