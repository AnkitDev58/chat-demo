package com.example.chatdemo.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment


fun Context.toast(s: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, s, length).show()
}

fun Context.createAnyDialog(view: View): AlertDialog = AlertDialog.Builder(this).let {
    it.setCancelable(false)
    it.setView(view)
    val x = it.create()
    x.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    x.setCanceledOnTouchOutside(false)
    x
}

fun AlertDialog.shows() {
    if (!this.isShowing) this.show()
}

fun AlertDialog.dismisss() {
    if (isShowing) this.dismiss()
}

fun <T> Fragment.getModel(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arguments?.getParcelable(key, clazz)
    } else {
        arguments?.getParcelable(key)
    }
}
