package com.wanho.data.repository

import com.wanho.data.local.PlaceLocalSource
import com.wanho.data.entity.PlaceEntity
import com.wanho.data.remote.PlaceRemoteSource
import com.wanho.data.response.KakaoResponse
import com.wanho.domain.repository.PlaceRepository

class PlaceRepositoryImpl(private val placeRemoteSource: PlaceRemoteSource, private val placeLocalSource: PlaceLocalSource) : PlaceRepository {

    override suspend fun getPlaceRemoteSource(latitude: String, longitude: String, radius: Int): List<PlaceEntity> {
        val placeList = mutableListOf<PlaceEntity>()

        // Kakao Response page 수 최대값 : 45
        for(page in 1..45) {
            val response: KakaoResponse = placeRemoteSource.getKakaoResponse(page, latitude, longitude, radius)

            val meta = response.meta
            val documents = response.documents

            // meta.isEnd : 마지막 검색 결과가 마지막일 경우 처리
            if(meta.isEnd) {
                break
            }

            // 마지막 페이지가 아닐 경우
            for (document in documents) {

                // 전화번호가 없을 경우 처리
                if(document.phone == "") {
                    document.phone = "전화번호 정보 없음"
                }

                placeList.add(PlaceEntity(document.id, document.placeName, document.categoryName, document.phone, document.distance.toInt(), document.placeUrl, document.roadAddressName, null, document.y.toDouble(), document.x.toDouble()))
            }
        }

        return placeList

    }



    override fun getHistoryLocalSource(): List<PlaceEntity> {
        return placeLocalSource.getHistoryList()
    }

    override fun setHistoryLocalSource(id : String, name: String, category: String, phone: String, distance : Int, placeUrl : String, roadAddressName : String, date: String? ,latitude: Double, longitude: Double) {
        val placeEntity = PlaceEntity(id, name, category, phone, distance, placeUrl, roadAddressName, date, latitude, longitude)
        placeLocalSource.setHistory(placeEntity)
    }

    override fun deleteAllLocalSource() {
        placeLocalSource.deleteAll()
    }

    override fun deleteHistoryLocalSource(id : String) {
        placeLocalSource.delete(id)
    }


}