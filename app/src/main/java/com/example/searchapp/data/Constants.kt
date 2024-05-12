package com.example.searchapp.data

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


object Constants {

    const val BASE_URL = "https://dapi.kakao.com/"

    const val REST_API_KEY = "e49d843a6ab84d8317a45991443daa0c"

    // 이미지 검색을 나타내는 상수
    const val SEARCH_TYPE_IMAGE = 0

    // 비디오 검색을 나타내는 상수
    const val SEARCH_TYPE_VIDEO = 1
}


// 날짜 포멧 변경
object Dateformat{
    @SuppressLint("SimpleDateFormat")
    fun dateformat(date : String) : String{
        val now = System.currentTimeMillis()    // 현재 시간 가져오기
        val dataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val result = dataFormat.format(now)
        return result
    }
}



