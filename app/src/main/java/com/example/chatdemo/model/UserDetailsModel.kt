package com.example.chatdemo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "userDetails", indices = [Index(value = ["uid"], unique = true)])

@Parcelize
data class UserDetailsModel(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var uid: String? = null,
    var name: String? = null,
    var lastOnline: String? = null,
    var phoneNumber: String? = null,
    var lastOnlineMili: Long? = null
) : Parcelable