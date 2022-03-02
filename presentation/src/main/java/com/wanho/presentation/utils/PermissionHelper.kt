package com.wanho.presentation.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// 위험 권한을 요청하기 쉽도록 별도의 클래스화 시킴
class PermissionHelper(requestCode : Int) {

    private val REQUEST_CODE = requestCode

    fun requestLocationPermission(activity: Activity, permission : String,  success:() -> Unit) {
        val preference = activity.getPreferences(AppCompatActivity.MODE_PRIVATE)
        val firstCheck = preference.getBoolean("firstPermissionCheck", true)
        if(ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            // 권한 없음
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // 권한 거절을 클릭하여 다시 물어볼 경우
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("해당 앱을 사용하기 위해서는 해당 권한이 필요합니다.")
                builder.setPositiveButton("동의") { dialog, which ->
                    ActivityCompat.requestPermissions(activity, arrayOf(permission), REQUEST_CODE)

                }
                builder.setNegativeButton("거절") {dialog, which ->
                    Toast.makeText(activity, "권한이 거부되어 앱을 종료합니다.", Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
                builder.show()
            } else {
                if(firstCheck) {
                    //최초 권한 요청
                    preference.edit().putBoolean("firstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(activity, arrayOf(permission), REQUEST_CODE)
                } else {
                    // 다시 묻지 않음을 클릭햇을 경우
                    val builder = AlertDialog.Builder(activity)
                    builder.setMessage("해당 권한 동의를 위해 설정으로 이동합니다.")
                    builder.setPositiveButton("이동") {dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${activity.packageName}"))
                        activity.startActivity(intent)
                    }
                    builder.setNegativeButton("거절") {dialog, which ->
                        Toast.makeText(activity, "권한이 거부되어 앱을 종료합니다.", Toast.LENGTH_SHORT).show()
                        activity.finish()
                    }
                    builder.show()
                }
            }

        } else {
            success()
        }

    }
}