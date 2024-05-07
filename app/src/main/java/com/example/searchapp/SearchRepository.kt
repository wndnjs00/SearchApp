package com.example.searchapp

import com.example.searchapp.retrofit.SearchRemoteDataSource

interface SearchRepository {
    suspend fun searchImages(
        query : String,
        sort : String,
        size : Int,
        page : Int
    ): SearchRemoteDataSource
}