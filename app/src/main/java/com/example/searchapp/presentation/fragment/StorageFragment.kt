package com.example.searchapp.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.databinding.FragmentStorageBinding
import com.example.searchapp.presentation.viewmodel.SearchViewModel
import com.example.searchapp.presentation.viewmodel.SearchViewModelFactory
import com.example.searchapp.presentation.viewmodel.StorageViewModel
import com.example.searchapp.presentation.viewmodel.StorageViewModelFactory
import com.google.gson.Gson


class StorageFragment : Fragment() {
    private var _binding : FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private val storageAdapter : StorageAdapter by lazy {
        StorageAdapter(searchList = ArrayList())
    }

    private val storageViewModel by viewModels<StorageViewModel> {
        StorageViewModelFactory()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStorageBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        // 로그 테스트
//        loadPrefsStorageItmes()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    // 어뎁터와 리사이클러뷰 연결
    private fun setRecyclerView(){

        with(binding.recyclerViewStorage){
            adapter = storageAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


    // searchFragment에 저장한리스트값 불러오기
    private fun loadPrefsStorageItmes(){
        val pref = activity?.getSharedPreferences("favorite_prefs",0)
        // 보관함에 저장되어있는 아이템
        val storageData = pref?.getString("STORAGE_ITEMS", null)

        Log.d("storageData",storageData.toString())
    }

}