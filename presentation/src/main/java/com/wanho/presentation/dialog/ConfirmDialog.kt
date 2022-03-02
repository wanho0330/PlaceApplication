package com.wanho.presentation.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.wanho.domain.model.PlaceModel
import com.wanho.presentation.databinding.DialogConfirmBinding
import com.wanho.presentation.history.HistoryViewModel
import com.wanho.presentation.result.PlaceViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


// 리스트 아이템 클릭했을 시 생성되는 다이얼로그
class ConfirmDialog(private val placeModel: PlaceModel) : DialogFragment(), DialogInterface{



    private lateinit var binding: DialogConfirmBinding
    private val placeViewModel : PlaceViewModel by sharedViewModel()
    private val historyViewModel : HistoryViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        settingBackKey()

        return dialog
    }

    @SuppressLint("NewApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogConfirmBinding.inflate(inflater, container, false)

        binding.view = this
        binding.model = placeModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }


    // 뒤로가기 클릭 시 다이얼로그 종료 함수
    private fun settingBackKey() {
        dialog?.setOnKeyListener { dialogInterface, keyCode, keyEvent ->
            if(keyCode == android.view.KeyEvent.KEYCODE_BACK && keyEvent.action == KeyEvent.ACTION_UP) {
                onDismiss(dialogInterface)
            }
            false
        }
    }


    // [상세] 클릭
    fun settingDetailButton() {
        val detailIntentUri = Uri.parse("${placeModel.placeUrl}")
        val detailIntent = Intent(Intent.ACTION_VIEW, detailIntentUri )

        startActivity(detailIntent)
    }


    // [다시] 클릭
    fun settingReplyButton() {
        placeViewModel.onRandomButtonClick()
        onDismiss(this)
    }

    // [확인] 클릭
    @RequiresApi(Build.VERSION_CODES.O)
    fun settingOkButton() {
        historyViewModel.setHistory(placeModel.id, placeModel.name, placeModel.category, placeModel.phone, placeModel.distance, placeModel.placeUrl, placeModel.roadAddressName, placeModel.latitude, placeModel.longitude)

        val gmmIntentUri = Uri.parse("geo:0.0?q=${placeModel.roadAddressName}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        startActivity(mapIntent)

        onDismiss(this)
    }

    // 오른쪽 위 [X] 클릭
    fun settingCancelButton() {
        onDismiss(this)
    }


    // 다이얼로그 Fragment 크기 조정
    private fun Context.dialogFragmentResize(width: Float) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var deviceWidth = 0

        val params:ViewGroup.LayoutParams? = dialog?.window?.attributes

        if (Build.VERSION.SDK_INT < 30) {

            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)
            deviceWidth = size.x

        } else {
            val rect = windowManager.currentWindowMetrics.bounds
            deviceWidth = rect.width()
        }

        params?.width = (deviceWidth * width).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }


    // 크기 조정을 위해 오버라이딩
    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(0.9f)
    }

    override fun cancel() {
        this.onDismiss(this)
    }




}