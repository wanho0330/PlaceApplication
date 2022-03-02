package com.wanho.presentation.search

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 내 위치 및 검색 범위를 지정하는 뷰모델
class LocationViewModel : ViewModel() {


    private var _location: MutableLiveData<Location> = MutableLiveData()
    val location: LiveData<Location>
        get() = _location

    private var _radius: MutableLiveData<Int> = MutableLiveData()
    val radius: LiveData<Int>
        get() = _radius

    val INIT_RADIUS : Int = 500

    // 내 위치를 정하는 함수
    fun setLocation(myLocation: Location) {
        _location.value = myLocation
    }

    // 검색 범위를 정하는 함수
    fun setRadius(myRadius : Int) {
        _radius.value = myRadius
    }

}