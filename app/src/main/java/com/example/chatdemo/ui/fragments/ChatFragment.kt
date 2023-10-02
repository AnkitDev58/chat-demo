package com.example.chatdemo.ui.fragments

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.chatdemo.base.BaseFragment
import com.example.chatdemo.databinding.FragmentChatBinding
import com.example.chatdemo.model.UserDetailsModel
import com.example.chatdemo.ui.adapters.ChatAdapter
import com.example.chatdemo.utils.getModel
import com.example.chatdemo.vm.ChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "ChatFragment"

class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>() {

    private val adapter by lazy {
        ChatAdapter(requireContext())
    }
    private val userDetailsModel by lazy { getModel("userInfo", UserDetailsModel::class.java) }

    override fun initUI() {
        binding.txtName.text = "${userDetailsModel?.name}"
        binding.txtLastMsg.text="${userDetailsModel?.phoneNumber}"
        binding.recyclerChat.adapter = adapter

    }

    override fun getVB(): FragmentChatBinding =
        FragmentChatBinding.inflate(LayoutInflater.from(context))

    override fun getVM(): ChatViewModel = activityViewModels<ChatViewModel>().value

    override fun listeners() {
        super.listeners()
        binding.imgSend.setOnClickListener {
            val message = binding.edtMessage.text?.trim().toString()
            if (message.isNotEmpty()) {
                binding.edtMessage.setText("")
                viewModel.sendMessage(userDetailsModel, message)
            }
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observers() {
        super.observers()
        viewModel.appDao.getUserChat(
            "${viewModel.sharePref.userUUID}${userDetailsModel?.uid ?: ""}"
        ).observe(this) {
            adapter.submitList(it)
            lifecycleScope.launch(Dispatchers.Main) {
                delay(100)
                binding.recyclerChat.scrollToPosition(adapter.currentList.size - 1)
            }
        }
    }
}