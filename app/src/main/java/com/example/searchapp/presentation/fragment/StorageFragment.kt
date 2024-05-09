package com.example.searchapp.presentation.fragment

import android.annotation.SuppressLint
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
import com.example.searchapp.presentation.viewmodel.StorageViewModel
import com.example.searchapp.presentation.viewmodel.StorageViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        updateData()
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



    private fun updateData(){

        // ViewModel을 observe해서 실시간 변경되는 데이터관찰
        storageViewModel.getStorageLiveData.observe(viewLifecycleOwner){
            Log.d("itData", it.toString())         // 전체 데이터

            // 데이터 업데이트
            this.storageAdapter.submitList(ArrayList(it))
            this.storageAdapter.notifyDataSetChanged()
        }

    }
}