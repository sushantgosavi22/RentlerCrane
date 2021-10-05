package com.sushant.sampledemomvvmapicall.views.base

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.views.adapter.AdapterItem
import com.sushant.sampledemomvvmapicall.views.adapter.ItemAdapter
import com.sushant.sampledemomvvmapicall.views.vehicaldetail.Vehicle

abstract class BaseListingFragment<VB : ViewBinding, itemClass : AdapterItem> : BaseFragment<VB>(), ItemAdapter.IAdapterItemListener<itemClass> {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var errorView: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private val adapter by lazy {
        ItemAdapter(ArrayList(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorView = binding.root.findViewById(R.id.errorView)
        recyclerView = binding.root.findViewById(R.id.recyclerView)
        swipeRefreshLayout = binding.root.findViewById(R.id.swipeRefreshLayout)
        initAdapter()
    }

    protected fun showErrorView(errorMessage : String? ="" ,error: Boolean = true) {
        errorView?.text = errorMessage
        errorView?.visibility = if (error) View.VISIBLE else View.GONE
    }

    protected fun initAdapter() {
        recyclerView?.layoutManager = getLayoutManager()
        recyclerView?.adapter = adapter
        swipeRefreshLayout?.setOnRefreshListener {
            onRefresh()
        }
    }

    protected fun handleSuccessResponse(list: ArrayList<itemClass>) {
        if(!list.isNullOrEmpty()){
            adapter.setList(list)
        }
    }

   open fun getLayoutManager() : RecyclerView.LayoutManager{
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        return layoutManager
    }


    fun onRefresh() {

    }
}