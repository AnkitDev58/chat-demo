package com.example.chatdemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.chatdemo.databinding.DialogLoadingBinding
import com.example.chatdemo.utils.createAnyDialog
import com.example.chatdemo.utils.dismisss
import com.example.chatdemo.utils.shows

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private lateinit var _vb: VB
    private lateinit var _vm: VM
    val binding get() = _vb
    val viewModel get() = _vm

    private val bindingDialog by lazy { DialogLoadingBinding.inflate(LayoutInflater.from(context)) }
    private val loadingDialog by lazy { context?.createAnyDialog(bindingDialog.root) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = getVB()
        _vm = getVM()
        return _vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        listeners()
        observers()
    }

    open fun observers() {}
    open fun listeners() {}
    abstract fun initUI()
    abstract fun getVB(): VB
    abstract fun getVM(): VM

    fun showHideDialog(it: Boolean?) {
        if (it == true) loadingDialog?.shows() else loadingDialog?.dismisss()
    }
}