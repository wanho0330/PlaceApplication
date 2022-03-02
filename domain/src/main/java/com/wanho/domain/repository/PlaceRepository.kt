package com.wanho.domain.repository

import com.wanho.domain.model.PlaceModel

interface PlaceRepository {
    // RemoteSource 사용 함수 : (위도, 경도) -> List
    // Retrofit을 이용하기 때문에 Coroutines 사용
    suspend fun getPlaceRemoteSource(latitude : String, longitude : String, radius : Int) : List<PlaceModel>

    // LocalSource 사용 함수
    fun getHistoryLocalSource() : List<PlaceModel>
    fun setHistoryLocalSource(id : String, name: String, category: String, phone: String, distance : Int, placeUrl : String, roadAddressName : String, date: String? , latitude : Double, longitude : Double)
    fun deleteAllLocalSource()
    fun deleteHistoryLocalSource(id : String)
}