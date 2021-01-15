package com.ibrahimdemir.moviesapp.extensions

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.ibrahimdemir.moviesapp.R

fun Activity.fullScreen() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

fun Activity.createProgressDialog(isCancelable: Boolean = false): Dialog = Dialog(this).apply {
    setCancelable(isCancelable)
    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    setContentView(R.layout.view_progress)
}