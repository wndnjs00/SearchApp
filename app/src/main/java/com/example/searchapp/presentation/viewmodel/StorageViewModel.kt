package com.example.searchapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchapp.data.SharedPreferences
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.data.network.RetrofitClient
import com.example.searchapp.data.remote.SearchRemoteDataSource

class StorageViewModel(private val remoteDataSource: SearchRemoteDataSource) : ViewModel(){

    private val _getStorageImageLiveData = MutableLiveData<List<DocumentResponse>>()
    val getStorageImageLiveData : LiveData<List<DocumentResponse>> get() = _getStorageImageLiveData

    // 지정된 북마크 아이템들을 가져오는 함수
    fun getBookmarkedItems(context: Context?){
        _getStorageImageLiveData.value = SharedPreferences.getPrefBookmarkItems(context)
    }


    class StorageViewModelFactory : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return StorageViewModel(RetrofitClient.searchRemoteDataSource) as T
        }
    }
}