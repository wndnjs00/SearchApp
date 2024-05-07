package com.example.searchapp.data.network

import com.example.searchapp.data.Constants
import com.example.searchapp.data.remote.SearchRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // 카카오 API base url
    private const val BASE_URL = Constants.BASE_URL


    // 네트워크 요청을 위한 httpClient 구성 (okHttpClient - 로깅인터셉터)
    private val okHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()
    }

    // retrofit 객체 초기화 및 생성
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            // Gson으로 컨버팅
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val searchRemoteDataSource : SearchRemoteDataSource by lazy {
        retrofit.create(SearchRemoteDataSource::class.java)
    }
}