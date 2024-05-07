package com.example.searchapp

import com.example.searchapp.retrofit.SearchRemoteDataSource

//  API 가이드 문서에서 확인한 Parameter를 이용해 DB에 데이터를 요청하는 역할
class SearchRepositoryImpl : SearchRepository{
    override suspend fun searchImages(
        query: String,
        sort: String,
        size: Int,
        page: Int
    ): SearchRemoteDataSource {
        return searchImages(query,sort,size = 80, page = 1)
    }
}