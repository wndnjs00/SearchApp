package com.example.searchapp.data.model

import com.google.gson.annotations.SerializedName

// 이미지 검색을 위한 데이터 클래스

data class SearchResponse(
    @SerializedName("meta") val searchMeta : MetaResponse,
    @SerializedName("documents") val searchDocuments : List<DocumentResponse>
)

data class MetaResponse(
    @SerializedName("is_end") val isEnd : Boolean,
    @SerializedName("pageable_count") val pageCount : Int,
    @SerializedName("total_count") val totalCount : Int
)

data class DocumentResponse(
    @SerializedName("collection") val collection : String,
    @SerializedName("datetime") val datetime : String,     //String으로 써주기
    @SerializedName("display_sitename") val displaySiteName : String,
    @SerializedName("doc_url") val docUrl : String,
    @SerializedName("height") val height : Int,
    @SerializedName("image_url") val imageUrl : String,
    @SerializedName("thumbnail_url") val thumbnailUrl : String,
    @SerializedName("width") val width : Int,
    var isLike : Boolean = false,    // 좋아요 처리 (클릭O-> true, 클릭X -> false)
    var type : Int  // 이미지, 비디오 타입 구분
)