package com.example.chatdemo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.chatdemo.MainActivity
import com.example.chatdemo.R
import com.example.chatdemo.base.BaseActivity
import com.example.chatdemo.databinding.ActivityStartBinding
import com.example.chatdemo.vm.LoginSignUpVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : BaseActivity<ActivityStartBinding, LoginSignUpVM>() {


    override fun initUI() {
        viewModel.checkUserExist(viewModel.sharePref.userUUID)
    }

    override fun getVB(): ActivityStartBinding = ActivityStartBinding.inflate(layoutInflater)
    override fun getVM(): LoginSignUpVM = viewModels<LoginSignUpVM>().value

    override fun observers() {
        super.observers()
        viewModel.isExistUser.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginSignUpActivity::class.java))
            }
            finish()
        }
    }
}