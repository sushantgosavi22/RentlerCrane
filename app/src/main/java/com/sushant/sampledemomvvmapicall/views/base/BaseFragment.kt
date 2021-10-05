package com.sushant.sampledemomvvmapicall.views.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

  /*  protected var viewModel: BaseViewModel? = null
    abstract fun <T : BaseViewModel?> getViewModel(modelClass: Class<T>): T?*/

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    protected lateinit var binding : VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflateViewBinding(inflater, container)
        setUpView()
        setUpObservable()
        return binding.root
    }

    open fun setUpView(){}
    open fun setUpObservable(){}


    fun showProgressBar(){
       if( activity is BaseActivity){
           (activity as BaseActivity).showProgressBar()
       }
    }

    /**
     * Hide progress bar.
     */
    fun hideProgressBar(){
        if( activity is BaseActivity){
            (activity as BaseActivity).hideProgressBar()
        }
    }
}