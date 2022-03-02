package com.wanho.data.remote

import com.wanho.data.response.KakaoResponse

class PlaceRemoteSourceImpl : PlaceRemoteSource {
    override suspend fun getKakaoResponse(page: Int, latitude : String, longitude : String, radius : Int): KakaoResponse {

        // FD6 = 식당
        return RetrofitClient.getService()
            .requestSearchPlace(page, "FD6", latitude, longitude, radius)
    }
}