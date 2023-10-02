package com.example.chatdemo.ui.fragments

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.chatdemo.R
import com.example.chatdemo.base.BaseActivity
import com.example.chatdemo.base.BaseFragment
import com.example.chatdemo.databinding.FragmentLoginBinding
import com.example.chatdemo.utils.toast
import com.example.chatdemo.vm.LoginSignUpVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginSignUpVM>() {

    override fun initUI() {
    }

    override fun getVB(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    override fun getVM(): LoginSignUpVM = activityViewModels<LoginSignUpVM>().value

    override fun listeners() {
        super.listeners()
        binding.btnSendOTP.setOnClickListener {
            checkPhoneNumber()
        }
    }

    private fun checkPhoneNumber() {
        val number = binding.edtNumber.text?.trim().toString()
        if (number.isEmpty()) {
            context?.toast("Enter your phone number")
        } else if (number.length < 10) {
            context?.toast("Enter valid phone number")
        } else {
            viewModel.sendOTP(
                "${binding.ipNumber.prefixText}$number",
                requireActivity() as BaseActivity<*, *>
            )
        }
    }

    override fun observers() {
        super.observers()
        viewModel.dialogState.observe(this) {
            showHideDialog(it)
        }

        viewModel.codeSent.observe(this) {
            if (it != null) {
                findNavController().navigate(R.id.OTPFragment)
            }
        }
    }
}