package com.example.searchapp.retrofit

import com.example.searchapp.retrofit.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// 통신하는 부분
interface SearchRemoteDataSource{
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

}
