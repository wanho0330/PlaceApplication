package com.wanho.presentation.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.wanho.domain.usecase.GetRemotePlaceUseCase
import com.wanho.domain.model.PlaceModel
import com.wanho.presentation.utils.SingleLiveEvent
import java.util.*

// 검색 결과 및 내 위치를 저장하는 뷰모델
class PlaceViewModel(private val getRemotePlaceUseCase: GetRemotePlaceUseCase) :
    ViewModel() {

    private var _placeList: MutableLiveData<List<PlaceModel>> = MutableLiveData()
    val placeList: LiveData<List<PlaceModel>>
        get() = _placeList

    private var _latitude: Double = 0.0
    val latitude: Double
        get() = _latitude

    private var _longitude: Double = 0.0
    val longitude: Double
        get() = _longitude

    private var _radius: Int = 0
    val radius: Int
        get() = _radius

    val singleLiveEvent: SingleLiveEvent<PlaceModel> = SingleLiveEvent()

    // 내 위치를 저장하는 함수
    fun setSearchOptions(myLatitude: Double, myLongitude: Double, myRadius: Int) {
        _latitude = myLatitude
        _longitude = myLongitude
        _radius = myRadius
    }

    // 근처 장소를 가져오는 함수
    fun getPlaceRepository() {

        getRemotePlaceUseCase.invoke(latitude.toString(), longitude.toString(), radius, viewModelScope) {
            _placeList.value = it
        }
    }

    fun onItemClick(placeModel: PlaceModel) {
        singleLiveEvent.value = placeModel
    }

    // 랜덤 버튼 클릭 시 실행되는 함수
    fun onRandomButtonClick() {
        if(_placeList.value != null) {
            val random = Random()
            val num = random.nextInt(_placeList.value!!.size)
            onItemClick(_placeList.value!![num])
        }
        else {
            Log.d("PlaceViewModel-tag","_placeList.value is null")
        }
    }

    // Result 액티비티 GoogleMap 카메라 이동 범위 지정 함수 : () -> 카메라 범위
    fun getCameraBounds() : LatLngBounds {
        var maxLatitude : Double = 0.0
        var maxLongitude : Double = 0.0

        var minLatitude : Double = 999.99
        var minLongitude : Double = 999.99

        _placeList.value?.forEach { placeModel ->
            if(placeModel.latitude > maxLatitude )
                maxLatitude = placeModel.latitude

            if(placeModel.longitude > maxLongitude)
                maxLongitude = placeModel.longitude

            if(placeModel.latitude < minLatitude)
                minLatitude = placeModel.latitude

            if(placeModel.longitude < minLongitude)
                minLongitude = placeModel.longitude
        }


        return LatLngBounds(
            LatLng(minLatitude, minLongitude),
            LatLng(maxLatitude, maxLongitude)
        )


    }



}