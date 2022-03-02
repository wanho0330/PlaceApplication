package com.wanho.presentation.utils

import android.app.AlertDialog
import android.content.Context

// 확인 다이얼로그를 쉽게 생성함
class AlertDialogHelper(private val context: Context) {

    fun dialogCreate(title : String, message : String, positiveFunc: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("확인") { dialog, i ->
                positiveFunc()
            }
            .setNegativeButton("취소") { dialog, i ->

            }
            .show()
    }
}