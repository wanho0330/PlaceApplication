package com.wanho.data.remote

import com.wanho.data.response.KakaoResponse

interface PlaceRemoteSource {

    // Kakao Location Api 에서 값 받아옴
    suspend fun getKakaoResponse(page: Int, latitude : String, longitude : String, radius : Int) : KakaoResponse
}