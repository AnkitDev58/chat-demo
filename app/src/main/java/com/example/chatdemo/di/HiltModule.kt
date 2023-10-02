package com.example.chatdemo.di

import android.content.Context
import androidx.room.Room
import com.example.chatdemo.db.AppDao
import com.example.chatdemo.db.AppDaoImpl
import com.example.chatdemo.db.AppDataBase
import com.example.chatdemo.utils.SharePref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "AppDataBase").build()
    }

    @Provides
    @Singleton
    fun getAppDao(appDataBase: AppDataBase): AppDaoImpl = AppDaoImpl(appDataBase.getDao())

    @Provides
    @Singleton
    fun getSharePref(@ApplicationContext context: Context) = SharePref(context)

}