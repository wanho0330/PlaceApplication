package com.wanho.presentation.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.wanho.presentation.R
import com.wanho.presentation.databinding.DialogLoadingBinding


// 데이터를 받아올 때 보여주기 위한 로딩 다이얼로그
class LoadingDialog(context: Context) : Dialog(context) {



    init {
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_loading)
    }



}