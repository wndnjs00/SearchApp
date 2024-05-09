package com.example.searchapp.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "my_storage")

// MyEntity클래스에 들어갈 데이터 지정
data class MyEntity (
    val collection : String,
    val datetime : String,
    val displaySiteName : String,
    val docUrl : String,
    val height : Int,
    val imageUrl : String,
    @PrimaryKey @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl : String,
    val width : Int,
    var isLike : Boolean = false    // 좋아요 처리 (클릭O-> true, 클릭X -> false)
)