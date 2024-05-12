package com.example.searchapp.data.model

import com.google.gson.annotations.SerializedName

// 비디오 검색을 위한 데이터 클래스
data class VideoResponse(
    @SerializedName("meta") val videoMeta : VideoMetaResponse,
    @SerializedName("documents") val videoDocuments : List<VideoDocumentResponse>
)

data class VideoMetaResponse(
    @SerializedName("is_end") val isEnd : Boolean,
    @SerializedName("pageable_count") val pageCount : Int,
    @SerializedName("total_count") val totalCount : Int
)

data class VideoDocumentResponse(
    @SerializedName("author") val author : String,
    @SerializedName("datetime") val datetime : String,
    @SerializedName("play_time") val playTime : Int,
    @SerializedName("thumbnail") val thumbNail : String,
    @SerializedName("title") val title : String,
    @SerializedName("url") val url : String,
    var isLike : Boolean = false,
    var type : Int  // 이미지, 비디오 타입 구분
)