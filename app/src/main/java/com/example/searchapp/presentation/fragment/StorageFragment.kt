package com.example.searchapp.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.databinding.FragmentStorageBinding
import com.example.searchapp.presentation.main.MainActivity
import com.example.searchapp.presentation.viewmodel.StorageViewModel
import com.example.searchapp.presentation.viewmodel.StorageViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class StorageFragment : Fragment() {
    private var _binding : FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private val storageAdapter : StorageAdapter by lazy {
        StorageAdapter(searchList = ArrayList()){ search, position ->
            adapterClick(position)
        }
    }


    // 사용자의 좋아요를 받은 항목을 저장하는 빈리스트
    private var likeItem : List<DocumentResponse> = listOf()


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

        // MainActivity로부터 좋아요받은 항목을 가져옴
        likeItem = (activity as MainActivity).likedItems
        Log.d("likeItems" , likeItem.size.toString())   // 잘 가져옴

        // 좋아요 받은 아이템 넣어서 업데이트
        storageAdapter.updateData(likeItem)
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


    private fun adapterClick(position : Int){

        // postion이 이상,,,
        Log.d("positions", position.toString())

        storageAdapter.searchList.removeAt(position)
        storageAdapter.notifyItemRemoved(position)
    }

}