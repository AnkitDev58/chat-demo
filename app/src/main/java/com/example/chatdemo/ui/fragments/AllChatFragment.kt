package com.example.chatdemo.ui.fragments

import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.chatdemo.R
import com.example.chatdemo.base.BaseFragment
import com.example.chatdemo.databinding.FragmentAllChatBinding
import com.example.chatdemo.ui.adapters.AllUserAdapter
import com.example.chatdemo.vm.ChatViewModel

class AllChatFragment : BaseFragment<FragmentAllChatBinding, ChatViewModel>() {

    private val adapter by lazy {
        AllUserAdapter()
    }

    override fun initUI() {
        binding.recyclerAllUser.adapter = adapter
    }


    override fun getVB(): FragmentAllChatBinding =
        FragmentAllChatBinding.inflate(LayoutInflater.from(context))

    override fun getVM(): ChatViewModel = activityViewModels<ChatViewModel>().value

    override fun observers() {
        super.observers()
        viewModel.allUser.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun listeners() {
        super.listeners()
        adapter.onUserClick = {
            findNavController().navigate(R.id.chatFragment, bundleOf("userInfo" to it))
        }
    }
}