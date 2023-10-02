package com.example.chatdemo.vm

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.chatdemo.base.BaseActivity
import com.example.chatdemo.model.UserDetailsModel
import com.example.chatdemo.utils.SharePref
import com.example.chatdemo.utils.toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "LoginSignUpVM"

@HiltViewModel
class LoginSignUpVM @Inject constructor(val context: Application, val sharePref: SharePref) :
    AndroidViewModel(context) {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val _database by lazy { Firebase.database }
    private val _allUsersRef by lazy { _database.getReference("allUsers") }

    private val _dialogState = MutableLiveData<Boolean>()
    val dialogState get() = _dialogState

    private val _codeSent = MutableLiveData<String?>()
    val codeSent get() = _codeSent
    private val _isExistUser = MutableLiveData<Boolean>()
    val isExistUser get() = _isExistUser
    private var _token: String? = null


    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Log.i(TAG, "onVerificationCompleted: $p0")
            _dialogState.postValue(false)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Log.i(TAG, "onVerificationFailed: $p0")
            _dialogState.postValue(false)
            _token = null
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(p0, p1)
            Log.i(TAG, "onCodeSent: $p0    $p1")
            context.toast("Code send Successfully")
            _dialogState.postValue(false)
            _token = p0
            _codeSent.postValue(_token)
        }

        override fun onCodeAutoRetrievalTimeOut(p0: String) {
            super.onCodeAutoRetrievalTimeOut(p0)
            Toast.makeText(context, "$p0", Toast.LENGTH_SHORT).show()
            _dialogState.postValue(false)
        }
    }
    private var phoneNumber: String? = null

    fun sendOTP(number: String, baseActivity: BaseActivity<*, *>) {
        _token = null
        phoneNumber = number
        _dialogState.postValue(true)
        Log.i(TAG, "phoneNumber: $number")
        val options = PhoneAuthOptions.newBuilder().setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(baseActivity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOTP(it: String?) {
        if (it != null) {
            _dialogState.postValue(true)
            val credential = PhoneAuthProvider.getCredential(_token ?: return, it)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            Log.i(TAG, "signInWithPhoneAuthCredential: ${it.isSuccessful}")
            if (it.isSuccessful) {
                val user = it.result.user
                checkUserExist(user?.uid)
            } else {
                _dialogState.postValue(false)
                when (it.exception) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        Toast.makeText(context, "${it.exception?.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    fun checkUserExist(user: String?) {
        user?.let {
            if (user.isEmpty()) {
                _isExistUser.postValue(false)
                return
            }
            sharePref.userUUID = user
            _allUsersRef.child(user)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        _dialogState.postValue(false)
                        _isExistUser.postValue(snapshot.exists())

                        Log.i(TAG, "onDataChange: $snapshot")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _dialogState.postValue(false)
                    }

                })
        }
    }

    fun saveName(name: String) {
        _allUsersRef.child(sharePref.userUUID).setValue(
            UserDetailsModel(
                uid = sharePref.userUUID,
                name = name,
                lastOnline = "Online",
                lastOnlineMili = System.currentTimeMillis(), phoneNumber = phoneNumber ?: ""
            )
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                _isExistUser.postValue(true)
            } else {
                Log.i(TAG, "saveName: $it")
            }
        }
    }
}