package com.example.searchapp.retrofit

import com.example.searchapp.retrofit.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// 통신하는 부분
interface SearchRemoteDataSource{
    @Headers("Authorization: KakaoAK e49d843a6ab84d8317a45991443daa0c")
    @GET("v2/search/image")

    // 카카오 Api에서 query값을 String타입으로 입력받음 (query는 필수값)
    // SearchResponse 상속
    suspend fun getSearch(
        @Query("query") query : String
    ): SearchResponse
}
