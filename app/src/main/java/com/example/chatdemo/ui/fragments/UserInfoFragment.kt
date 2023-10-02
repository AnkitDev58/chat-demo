package com.example.chatdemo.ui.fragments

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.chatdemo.MainActivity
import com.example.chatdemo.base.BaseFragment
import com.example.chatdemo.databinding.FragmentUserInfoBinding
import com.example.chatdemo.utils.toast
import com.example.chatdemo.vm.LoginSignUpVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, LoginSignUpVM>() {

    override fun initUI() {

    }

    override fun getVB(): FragmentUserInfoBinding = FragmentUserInfoBinding.inflate(layoutInflater)

    override fun getVM(): LoginSignUpVM = activityViewModels<LoginSignUpVM>().value


    override fun listeners() {
        super.listeners()
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text?.trim().toString()
            if (name.isEmpty()) context?.toast("Please fill name") else viewModel.saveName(name)
        }

        viewModel.isExistUser.observe(this.viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
    }

}