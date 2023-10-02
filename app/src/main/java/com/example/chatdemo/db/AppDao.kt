package com.example.chatdemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatdemo.model.ChatModel
import com.example.chatdemo.model.UserDetailsModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(model: UserDetailsModel)

    @Query("Select * from userDetails")
    fun getAllChatUser(): LiveData<List<UserDetailsModel>>

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insertChat(model: ChatModel)

    @Query("SELECT * from chat")
    fun allChat(): LiveData<List<ChatModel>>

    @Query("select * from chat where commonSenderReceiver=:commonSenderReceiver")
    fun getUserChat(commonSenderReceiver: String): LiveData<List<ChatModel>>

}