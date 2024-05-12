package com.example.searchapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.data.model.VideoDocumentResponse
import com.example.searchapp.data.network.RetrofitClient
import com.example.searchapp.data.remote.SearchRemoteDataSource
import kotlinx.coroutines.launch


class SearchViewModel(private val remoteDataSource: SearchRemoteDataSource) : ViewModel() {

    // 이미지 검색
    private val _getSearchImageLiveData : MutableLiveData<List<DocumentResponse>> = MutableLiveData()
    val getSearchImageLiveData : LiveData<List<DocumentResponse>> get() = _getSearchImageLiveData

    fun getSearchImageList(query : String) = viewModelScope.launch {
        _getSearchImageLiveData.value = remoteDataSource.getSearch(query).searchDocuments
    }


    // 비디오 검색
    private val _getSearchVideoLiveData : MutableLiveData<List<VideoDocumentResponse>> = MutableLiveData()
    val getSearchVideoLiveData : LiveData<List<VideoDocumentResponse>> get() = _getSearchVideoLiveData

    fun getSearchVideoList(query: String) = viewModelScope.launch {
        _getSearchVideoLiveData.value = remoteDataSource.getVideo(query).videoDocuments
    }

}

class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return SearchViewModel(RetrofitClient.searchRemoteDataSource) as T
    }
}


