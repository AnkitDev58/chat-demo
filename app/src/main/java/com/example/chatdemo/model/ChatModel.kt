package com.example.chatdemo.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "chat" ,indices = [Index(value = ["key"], unique = true)])

data class ChatModel(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var key: String? = null,
    var message: String? = null,
    var currentTIme: Long? = null,
    var senderUID: String? = null,
    var receiverUID: String? = null,
    var commonSenderReceiver:String?=null
)