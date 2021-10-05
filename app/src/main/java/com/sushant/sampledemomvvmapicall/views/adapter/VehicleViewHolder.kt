package com.sushant.sampledemomvvmapicall.views.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.databinding.ListItemBinding
import com.sushant.sampledemomvvmapicall.databinding.VehicleListItemBinding
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.views.assignmentdetails.AssignmentDetailsActivity
import com.sushant.sampledemomvvmapicall.views.vehicaldetail.Vehicle

class VehicleViewHolder(var mListItemBinding : VehicleListItemBinding) : BaseViewHolder<Vehicle>(mListItemBinding){
    override fun bind(model: Vehicle, position: Int, listenerI: ItemAdapter.IAdapterItemListener<Vehicle>?) {
        super.bind(model, position, listenerI)
        mListItemBinding.addAssignment.setOnClickListener {
            val intent = Intent(it.context,AssignmentDetailsActivity::class.java)
            intent.putExtra(Utils.EXTRA_PARAM_KEY,model.firebaseId)
            it.context.startActivity(intent)
        }
        mListItemBinding.item = model
    }

    companion object{
        fun getInstance(parent: ViewGroup) : VehicleViewHolder{
            val binding =VehicleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return VehicleViewHolder(binding)
        }
    }
}