package com.example.searchapp.data.repository

import com.example.searchapp.data.remote.SearchRemoteDataSource
import com.example.searchapp.presentation.repository.SearchRepository

//  API 가이드 문서에서 확인한 Parameter를 이용해 DB에 데이터를 요청하는 역할
class SearchRepositoryImpl : SearchRepository {
    override suspend fun searchImages(
        query: String,
        sort: String,
        size: Int,
        page: Int
    ): SearchRemoteDataSource {
        return searchImages(query,sort = "recency",size = 80, page = 1)
    }
}