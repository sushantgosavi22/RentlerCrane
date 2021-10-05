package com.sushant.sampledemomvvmapicall.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sushant.sampledemomvvmapicall.databinding.AssignmentListItemBinding
import com.sushant.sampledemomvvmapicall.views.assignmentdetails.Assignment

class AssignmentViewHolder(var mListItemBinding: AssignmentListItemBinding) : BaseViewHolder<Assignment>(mListItemBinding) {
    var imageUrl: String? = ""
    override fun bind(model: Assignment, position: Int, listenerI: ItemAdapter.IAdapterItemListener<Assignment>?) {
        model.vehicleImageUrl = imageUrl
        super.bind(model, position, listenerI)
        mListItemBinding.item = model
    }

    fun setVehicleImageUrl(url: String?) {
        imageUrl = url
    }

    companion object {
        fun getInstance(parent: ViewGroup): AssignmentViewHolder {
            val binding = AssignmentListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AssignmentViewHolder(binding)
        }
    }
}