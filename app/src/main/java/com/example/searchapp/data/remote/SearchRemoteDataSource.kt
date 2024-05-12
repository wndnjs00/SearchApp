package com.example.searchapp.data.remote

import com.example.searchapp.data.Constants
import com.example.searchapp.data.model.SearchResponse
import com.example.searchapp.data.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// 통신하는 부분
interface SearchRemoteDataSource{

    // 이미지 검색 통신
    @Headers("Authorization: KakaoAK ${Constants.REST_API_KEY}")
    @GET("v2/search/image")

    // 카카오 Api에서 query값을 String타입으로 입력받음 (query는 필수값)
    // SearchResponse 상속
    // api 문서에 request의 parameter를 인터페이스로 정의!
    suspend fun getSearch(
        @Query("query") query : String,
        @Query("sort") sort : String = "recency",
        @Query("size") size : Int = 80,
        @Query("page") page : Int = 1
    ): SearchResponse


    // 비디오 검색 통신
    @Headers("Authorization: KakaoAK ${Constants.REST_API_KEY}")
    @GET("v2/search/vclip")
    suspend fun getVideo(
        @Query("query") query : String,
        @Query("sort") sort : String = "recency",
        @Query("size") size : Int = 40,
        @Query("page") page : Int = 1
    ): VideoResponse
}
