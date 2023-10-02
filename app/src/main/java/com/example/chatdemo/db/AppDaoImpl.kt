package com.example.chatdemo.db

import androidx.lifecycle.LiveData
import com.example.chatdemo.model.ChatModel
import com.example.chatdemo.model.UserDetailsModel
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AppDaoImpl @Inject constructor(private val appDao: AppDao) : AppDao {
    override suspend fun insert(model: UserDetailsModel) {
        appDao.insert(
            model
        )
    }

    override fun getAllChatUser(): LiveData<List<UserDetailsModel>> {
        return appDao.getAllChatUser()
    }

    override suspend fun insertChat(model: ChatModel) {
        return appDao.insertChat(model)
    }

    override fun allChat(): LiveData<List<ChatModel>> {

        return appDao.allChat()
    }

    override fun getUserChat(commonSenderReceiver: String): LiveData<List<ChatModel>> {
        return appDao.getUserChat(commonSenderReceiver )
    }

    fun inserts(model: UserDetailsModel) {
        CoroutineScope(Dispatchers.IO).launch {
            insert(model)
        }
    }

    fun chatInsert(list: DataSnapshot) {
        CoroutineScope(Dispatchers.IO).launch {
            for (data in list.children) {
                val d = data.getValue(ChatModel::class.java)
                d?.let {
                    appDao.insertChat(d)
                }
            }
        }
    }
}