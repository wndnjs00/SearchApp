package com.example.searchapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Room database의 기본틀
@Database(entities = [MyEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase(){
    abstract fun getMyDao() : MyDao

    //  companion object를 만들어서 getDatabase라는 메소드만 호출하면 바로 불러올수있도록 (싱글톤 패턴으로 만들기)
    companion object{
        private var INSTANCE : MyDataBase? = null


        @Synchronized
        fun getInstance(context: Context) : MyDataBase? {
            if(INSTANCE == null){
                synchronized((MyDataBase :: class)){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, MyDataBase::class.java, "my_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}