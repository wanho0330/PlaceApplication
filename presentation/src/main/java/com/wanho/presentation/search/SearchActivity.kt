package com.wanho.presentation.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.material.slider.LabelFormatter
import com.wanho.presentation.history.HistoryActivity
import com.wanho.presentation.utils.PermissionHelper
import com.wanho.presentation.R
import com.wanho.presentation.result.ResultActivity
import com.wanho.presentation.databinding.ActivitySearchBinding
import com.wanho.presentation.dialog.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val PERMISSION_REQUEST_CODE = 1000

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var binding : ActivitySearchBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest : LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val locationViewModel: LocationViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.view = this
        binding.viewModel = locationViewModel
        binding.lifecycleOwner = this

        PermissionHelper(PERMISSION_REQUEST_CODE).requestLocationPermission(this, PERMISSION_FINE_LOCATION) { startLocationUpdates() }

        settingFragment(MapsFragment())
        settingSlider()
        settingDialog()
        settingToolabar()

    }

    // Top Toolbar 세팅하는 함수
    private fun settingToolabar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolBar.title = "Bob Administrator"
    }


    // 내 위치를 찾을 때 로딩 다이얼로그를 띄워주는 함수
    private fun settingDialog() {
        loadingDialog = LoadingDialog(this@SearchActivity)
        loadingDialog.show()

        locationViewModel.location.observe(this, {
            loadingDialog.dismiss()
        })
    }


    // 내 위치를 찾는 함수
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (myLocation in locationResult.locations) {
                    locationViewModel.setLocation(myLocation)
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    // 내 위치를 찾는것을 중단하는 함수 (배터리 및 메모리 효율을 위해 사용)
    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


    // 프레그먼트를 세팅하는 함수
    private fun settingFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.search_frame_layout, fragment)
            .commit()
    }

    // 슬라이더바에 따른 변화를 세팅하는 함수
    private fun settingSlider() {

        // 슬라이더바의 라벨형식을 세팅
        binding.distanceSlider.setLabelFormatter(object : LabelFormatter {
            override fun getFormattedValue(value: Float): String {
                return if(value >= 1000) {
                    if((value.toInt() % 1000) == 0) {
                        "${(value/1000).toInt()} Km"
                    } else {
                        "${(value/1000).toInt()} Km ${(value%1000).toInt()} m"
                    }
                } else {
                    "${value.toInt()} m"
                }
            }
        })

        // 슬라이더바의 값이 변할 때 처리
        binding.distanceSlider.addOnChangeListener { slider, value, fromUser ->
            locationViewModel.setRadius(value.toInt())
            binding.minuteTextView.text = "도보 소요시간은 약 ${(value/40).toInt()}분입니다."
        }
    }



    // [검색]을 클릭했을 때 실행되는 함수
    fun settingSearchButton() {

            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("latitude", locationViewModel.location.value?.latitude)
            intent.putExtra("longitude", locationViewModel.location.value?.longitude)
            intent.putExtra("radius", locationViewModel.radius.value)

            startActivity(intent)

    }



    // 메뉴 생성 및 메뉴 클릭 처리 담당
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.result_menu_top, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.history_item -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }
            else -> {

            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }


}