package com.sushant.sampledemomvvmapicall.views.rentlerdashboard.ui.vehicle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.databinding.FragmentVehicleBinding
import com.sushant.sampledemomvvmapicall.views.adapter.BaseViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.VehicleViewHolder
import com.sushant.sampledemomvvmapicall.views.base.BaseListingFragment
import com.sushant.sampledemomvvmapicall.views.vehicaldetail.Vehicle
import com.sushant.sampledemomvvmapicall.views.vehicaldetail.VehicleDetailActivity

class VehicleFragment : BaseListingFragment<FragmentVehicleBinding,Vehicle>() {

    private val VEHICLE_REQ_CODE = 100
    private lateinit var vehicleViewModel: VehicleViewModel

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentVehicleBinding {
        return  FragmentVehicleBinding.inflate(inflater, container, false)
    }


    override fun setUpView() {
        super.setUpView()
        vehicleViewModel =
            ViewModelProvider(this).get(VehicleViewModel::class.java)
        binding.addVehicle.setOnClickListener {
            startActivityForResult(Intent(requireContext(), VehicleDetailActivity::class.java),VEHICLE_REQ_CODE)
        }
        binding.includedToolbar.tvAppbarTitle.text = getString(R.string.vehicle_title)
        vehicleViewModel.fetchVehicle()
    }

    override fun setUpObservable() {
        super.setUpObservable()
        vehicleViewModel.dataBaseFetchVehicleResult.observe(viewLifecycleOwner, Observer {databaseSaveResult->
            when (databaseSaveResult) {
                is DatabaseResult.Success -> {
                    val list =  databaseSaveResult.data as ArrayList<Vehicle>
                    showErrorView( error = false)
                    handleSuccessResponse(list)
                }
                is DatabaseResult.Failure -> {
                    showErrorView(databaseSaveResult.exception.message,true)
                }
                is DatabaseResult.StartLoading -> {
                    showProgressBar()
                }
                is DatabaseResult.StopLoading-> {
                    hideProgressBar()
                }
            }
        })
    }


    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(requireContext(),2)

    override fun onItemClick(pos: Int, data: Vehicle?) {
        val bundle = Bundle()
        bundle.putSerializable("vehicle",data)
        bundle.putString("vehicleId",data?.firebaseId.toString())
        findNavController().navigate(R.id.navigation_home, bundle)
    }

    override fun getHolder(parent: ViewGroup): BaseViewHolder<Vehicle> {
        return VehicleViewHolder.getInstance(parent)
    }

}