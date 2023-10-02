package com.example.chatdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chatdemo.model.ChatModel
import com.example.chatdemo.model.UserDetailsModel


@Database(entities = [UserDetailsModel::class, ChatModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getDao(): AppDao
}