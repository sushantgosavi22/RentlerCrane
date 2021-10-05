package com.sushant.sampledemomvvmapicall.views.vehicaldetail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.databinding.ActivityVehicleDetailBinding
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import java.io.File

class VehicleDetailActivity : BaseActivity() {


    private val PICK_IMAGE_REQ_CODE = 100

    private val vehicle by lazy {
        val extra = intent.getSerializableExtra(Utils.EXTRA_PARAM)
        if (extra != null) extra as Vehicle else Vehicle().apply { firebaseId =-1L }
    }

    private lateinit var vehicleDetailViewModel: VehicleDetailViewModel
    private lateinit var binding: ActivityVehicleDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleDetailBinding.inflate(layoutInflater)
        binding.vehicle = vehicle
        setContentView(binding.root)
        vehicleDetailViewModel = ViewModelProvider(this).get(VehicleDetailViewModel::class.java)
        setUpView()
    }

    private fun setUpView() {
        vehicleDetailViewModel.dataBaseResult.observe(this, Observer { databaseSaveResult ->
            var message: String? = ""
            when (databaseSaveResult) {
                is DatabaseResult.Success -> {
                    message = databaseSaveResult.data as String
                    vehicle.clear()
                    binding.vehicleImage.setImageResource(R.drawable.jcb_logo)
                    binding.vehicle = vehicle

                    Utils.showToast(this@VehicleDetailActivity, message)
                }
                is DatabaseResult.Failure -> {
                    message = databaseSaveResult.exception.message
                    Utils.showToast(this@VehicleDetailActivity, message)
                }
                is DatabaseResult.StartLoading -> {
                    showProgressBar()
                }
                is DatabaseResult.StopLoading-> {
                    hideProgressBar()
                }
            }
        })

        binding.btnSaveVehicle.setOnClickListener {
            if (validate()) {
                vehicleDetailViewModel.saveVehicle(vehicle)
            }
        }
        binding.vehicleImage.setOnClickListener {
            ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)
                .saveDir(File(getCacheDir(), "ImagePicker"))
                //.galleryOnly()	//User can only select image from Gallery
                //.cameraOnly()
                .start(PICK_IMAGE_REQ_CODE)

           /* ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }*/
        }
    }

    private fun validate(): Boolean {
        return if (vehicle.number?.isNotEmpty() == true &&
            vehicle.modelName?.isNotEmpty() == true
        ) {
            true;
        } else {
            Utils.showToast(this, "Please enter all details first.")
            false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            when (requestCode) {
                PICK_IMAGE_REQ_CODE -> {
                   // val auxFile: File = File(uri.toString())
                    vehicle.imageUrl =uri.toString()
                    binding.vehicleImage.setImageURI(uri)
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

   /* private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data
                val auxFile: File = File(fileUri.toString())
                vehicle.imageUrl =auxFile.path
                binding.vehicleImage.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }*/
}