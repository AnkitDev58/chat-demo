package com.example.chatdemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    private lateinit var _vb: VB
    private lateinit var _vm: VM
    val binding get() = _vb
    val viewModel get() = _vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _vb = getVB()
        setContentView(_vb.root)
        _vm = getVM()
        initUI()
        listeners()
        observers()
    }

    open fun observers() {}
    open fun listeners() {}
    abstract fun initUI()
    abstract fun getVB(): VB
    abstract fun getVM(): VM

}