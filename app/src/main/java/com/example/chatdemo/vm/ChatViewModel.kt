package com.example.chatdemo.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.chatdemo.db.AppDaoImpl
import com.example.chatdemo.model.ChatModel
import com.example.chatdemo.model.UserDetailsModel
import com.example.chatdemo.utils.SharePref
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "ChatViewModel"

@HiltViewModel
class ChatViewModel @Inject constructor(
    context: Application, val appDao: AppDaoImpl, val sharePref: SharePref
) : AndroidViewModel(context) {


    private val _database by lazy { Firebase.database }
    private val _allUsersRef by lazy { _database.getReference("allUsers") }
    private val _allChat by lazy { _database.getReference("chat") }

    val allUser get() = appDao.getAllChatUser()

    init {
        _allUsersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (d in snapshot.children) {
                        val userDetails = d.getValue(UserDetailsModel::class.java)
                        if (userDetails?.uid != sharePref.userUUID) appDao.inserts(userDetails!!)
                        Log.i(TAG, "onDataChange: $userDetails")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        _allChat.child(sharePref.userUUID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    appDao.chatInsert(snapshot)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    fun sendMessage(userDetailsModel: UserDetailsModel?, message: String) {
        val key = _allChat.push().key ?: ""

        _allChat.child(sharePref.userUUID).child(key).setValue(
            ChatModel(
                key = key,
                message = message,
                currentTIme = System.currentTimeMillis(),
                senderUID = sharePref.userUUID,
                receiverUID = userDetailsModel?.uid,
                commonSenderReceiver = "${sharePref.userUUID}${userDetailsModel?.uid}"
            )
        )
        _allChat.child("${userDetailsModel?.uid}").child(key).setValue(
            ChatModel(
                key = key,
                message = message,
                currentTIme = System.currentTimeMillis(),
                senderUID = sharePref.userUUID,
                receiverUID = userDetailsModel?.uid,
                commonSenderReceiver = "${userDetailsModel?.uid}${sharePref.userUUID}"
            )
        )
    }

}