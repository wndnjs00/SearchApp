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

class StorageViewModel(private val remoteDataSource: SearchRemoteDataSource) : ViewModel() {

    private val _getStorageLiveData : MutableLiveData<List<DocumentResponse>> = MutableLiveData()
    val getStorageLiveData : LiveData<List<DocumentResponse>> get() = _getStorageLiveData

    fun getStorageImageList(qery: String) = viewModelScope.launch{
        _getStorageLiveData.value = remoteDataSource.getSearch(qery).searchDocuments
    }

}

class StorageViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StorageViewModel(RetrofitClient.searchRemoteDataSource) as T
    }
}