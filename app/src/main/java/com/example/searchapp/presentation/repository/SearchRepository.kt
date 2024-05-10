package com.example.searchapp.presentation.repository

import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.data.remote.SearchRemoteDataSource

interface SearchRepository {
    suspend fun searchImages(
        query : String,
        sort : String,
        size : Int,
        page : Int
    ): SearchRemoteDataSource

}