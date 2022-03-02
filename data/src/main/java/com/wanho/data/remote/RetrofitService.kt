package com.wanho.data.remote

import com.wanho.data.response.KakaoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    // 데이터 형식 샘플
    // https://dapi.kakao.com/v2/local/search/category.json?category_group_code=PM9&radius=20000

    // Response 형식은 KakaoResponse 그대로 사용 ( Coroutines 사용 )
    @Headers("Authorization: KakaoAK 6c1884836f2926f44a7e80f971572261")
    @GET("v2/local/search/category.json")
    suspend fun requestSearchPlace(
        @Query("page") page : Int,
        @Query("category_group_code") categoryGroupCode : String,
        @Query("y") latitude : String,
        @Query("x") longitude : String,
        @Query("radius") radius : Int
    ) : KakaoResponse

}