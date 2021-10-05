package com.sushant.sampledemomvvmapicall.views.rentlerdashboard.ui.assignment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.database.firebase.DatabaseResult
import com.sushant.sampledemomvvmapicall.databinding.FragmentAssignmentBinding
import com.sushant.sampledemomvvmapicall.views.adapter.AssignmentViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.BaseViewHolder
import com.sushant.sampledemomvvmapicall.views.assignmentdetails.Assignment
import com.sushant.sampledemomvvmapicall.views.assignmentdetails.AssignmentDetailsActivity
import com.sushant.sampledemomvvmapicall.views.base.BaseListingFragment

class AssignmentFragment : BaseListingFragment<FragmentAssignmentBinding, Assignment>()  {

    private lateinit var assignmentViewModel: AssignmentViewModel

    val args: AssignmentFragmentArgs by navArgs()

    private val vehicle by lazy {
        args.vehicle
    }

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAssignmentBinding {
        return  FragmentAssignmentBinding.inflate(inflater, container, false)
    }

    override fun onItemClick(pos: Int, data: Assignment?) {
        val intent = Intent(requireContext(),AssignmentDetailsActivity::class.java)
        intent.putExtra(Utils.EXTRA_PARAM,data)
        startActivity(intent)
    }

    override fun getHolder(parent: ViewGroup): BaseViewHolder<Assignment> {
        val holder = AssignmentViewHolder.getInstance(parent)
        holder.setVehicleImageUrl(vehicle?.imageUrl)
        return holder
    }

    override fun setUpView() {
        super.setUpView()
        assignmentViewModel =
            ViewModelProvider(this).get(AssignmentViewModel::class.java)
        binding.includedToolbar.tvAppbarTitle.text = if(vehicle!=null)  vehicle?.number  else getString(R.string.assignment_list_title)
        assignmentViewModel.fetchAssignment(vehicle?.firebaseId?:-1L)
    }

    override fun setUpObservable() {
        super.setUpObservable()
        assignmentViewModel.dataBaseFetchAssignmentResult.observe(viewLifecycleOwner, Observer {databaseSaveResult->
            when (databaseSaveResult) {
                is DatabaseResult.Success -> {
                    val list =  databaseSaveResult.data as ArrayList<Assignment>
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

}