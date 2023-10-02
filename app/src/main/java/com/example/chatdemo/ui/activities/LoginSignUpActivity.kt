package com.example.chatdemo.ui.activities

import androidx.activity.viewModels
import com.example.chatdemo.base.BaseActivity
import com.example.chatdemo.databinding.ActivityLoginSignUpBinding
import com.example.chatdemo.vm.LoginSignUpVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSignUpActivity : BaseActivity<ActivityLoginSignUpBinding, LoginSignUpVM>() {


    override fun initUI() {

    }

    override fun getVB(): ActivityLoginSignUpBinding =
        ActivityLoginSignUpBinding.inflate(layoutInflater)

    override fun getVM(): LoginSignUpVM = viewModels<LoginSignUpVM>().value


}