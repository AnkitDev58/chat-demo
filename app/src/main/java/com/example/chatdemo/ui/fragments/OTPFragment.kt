package com.example.chatdemo.ui.fragments

import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.chatdemo.MainActivity
import com.example.chatdemo.R
import com.example.chatdemo.base.BaseFragment
import com.example.chatdemo.databinding.FragmentOTPBinding
import com.example.chatdemo.vm.LoginSignUpVM
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "OTPFragment"


@AndroidEntryPoint
class OTPFragment : BaseFragment<FragmentOTPBinding, LoginSignUpVM>() {

    override fun initUI() {
        viewModel.codeSent.postValue(null)
    }

    override fun getVB(): FragmentOTPBinding = FragmentOTPBinding.inflate(layoutInflater)

    override fun getVM(): LoginSignUpVM = activityViewModels<LoginSignUpVM>().value

    override fun listeners() {
        super.listeners()
        binding.otpView.setOtpCompletionListener {
            viewModel.verifyOTP(it)
        }
    }

    override fun observers() {
        super.observers()
        viewModel.dialogState.observe(this.viewLifecycleOwner) {
            showHideDialog(it)
        }
        viewModel.isExistUser.observe(this.viewLifecycleOwner) {
            Log.i(TAG, "observers: $it")
            if (it) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
            } else {
                findNavController().navigate(R.id.userInfoFragment)
            }
        }
    }
}