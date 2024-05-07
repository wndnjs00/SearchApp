package com.example.searchapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.data.network.RetrofitClient
import com.example.searchapp.data.remote.SearchRemoteDataSource
import kotlinx.coroutines.launch


class SearchViewModel(private val remoteDataSource: SearchRemoteDataSource) : ViewModel() {

    private val _getSearchImageLiveData : MutableLiveData<List<DocumentResponse>> = MutableLiveData()
    val getSearchImageLiveData : LiveData<List<DocumentResponse>> get() = _getSearchImageLiveData

    fun getSearchImageList(query : String) = viewModelScope.launch {
        _getSearchImageLiveData.value = remoteDataSource.getSearch(query).searchDocuments
    }
}

class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return SearchViewModel(RetrofitClient.searchRemoteDataSource) as T
    }
}

