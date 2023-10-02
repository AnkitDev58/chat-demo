package com.example.chatdemo

import androidx.activity.viewModels
import com.example.chatdemo.base.BaseActivity
import com.example.chatdemo.databinding.ActivityMainBinding
import com.example.chatdemo.ui.adapters.AllUserAdapter
import com.example.chatdemo.vm.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, ChatViewModel>() {

    override fun initUI() {

    }

    override fun getVB(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun getVM(): ChatViewModel = viewModels<ChatViewModel>().value


}