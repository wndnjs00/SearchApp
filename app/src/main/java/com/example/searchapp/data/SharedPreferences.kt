package com.example.searchapp.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.example.searchapp.data.model.DocumentResponse
import com.google.gson.GsonBuilder

object SharedPreferences {


    // SharedPreferences를 통해 아이템을 추가하는 함수
    fun addPrefItem(context: Context?, item: DocumentResponse){
        val prefs = context?.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor = prefs?.edit()
        val gson = GsonBuilder().create()

        editor?.putString(item.imageUrl, gson.toJson(item))
        editor?.apply()
    }



    // SharedPreferences를 통해 특정 url을 키로하는 아이템을 삭제하는 함수
    @SuppressLint("CommitPrefEdits")
    fun deletePrefItem(context: Context?, item: DocumentResponse){
        val prefs = context?.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val editor = prefs?.edit()

        editor?.remove(item.imageUrl)
        editor?.apply()
    }


    // SharedPreferences를 통해 모든 북마크된 아이템을 가져오는 함수
    fun getPrefBookmarkItems(context: Context?) : ArrayList<DocumentResponse>{
        val prefs = context?.getSharedPreferences("pref", Activity.MODE_PRIVATE)

        val allEntries : Map<String, *> = prefs!!.all
        val bookmarkItems = ArrayList<DocumentResponse>()

        val gson = GsonBuilder().create()

        for ((key, value ) in allEntries){
            // gson을 다시 json형태(DocumentResponse)로 되돌려놓기
            val item = gson.fromJson(value as String, DocumentResponse::class.java)

            // bookmarkItems에 북마크된 item 추가
            bookmarkItems.add(item)
        }
        return bookmarkItems
    }
}