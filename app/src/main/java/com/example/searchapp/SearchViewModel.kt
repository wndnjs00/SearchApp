package com.example.searchapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.searchapp.retrofit.SearchRemoteDataSource
import com.example.searchapp.retrofit.model.DocumentResponse
import com.example.searchapp.retrofit.model.SearchResponse
import com.example.searchapp.retrofit.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
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


