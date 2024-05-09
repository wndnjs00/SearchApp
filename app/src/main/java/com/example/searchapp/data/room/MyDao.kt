package com.example.searchapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {

    // 전체 데이터 가져옴 (저장)
    @Query("SELECT * FROM my_storage")
    fun getAllStorageData() : LiveData<List<MyEntity>>

    // 전체중 thumbnail_url에 해당하는 데이터만 가져옴
    @Query("SELECT * FROM my_storage WHERE thumbnail_url = :sname")
    suspend fun getImageUrl(sname : String) : List<MyEntity>

    // 삽입 (추가)
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun inseartStorageData(myEntity : MyEntity)

    // 삭제
    @Delete
    suspend fun deleteStorageData(myEntity: MyEntity)

}