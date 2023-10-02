package com.example.chatdemo.utils

import android.content.Context
import javax.inject.Inject

class SharePref @Inject constructor(context: Context) {

    private val sharePref by lazy {
        context.getSharedPreferences(
            "sharePref", Context.MODE_PRIVATE
        )
    }

    private val editor by lazy {
        sharePref.edit()
    }


    var userUUID: String = ""
        get() {
            return sharePref.getString("userUUID", "") ?: ""
        }
        set(value) {
            field = value
            editor.putString("userUUID", value).apply()
        }

}