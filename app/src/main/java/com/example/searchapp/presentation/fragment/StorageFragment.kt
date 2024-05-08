package com.example.searchapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.databinding.FragmentStorageBinding


class StorageFragment : Fragment() {
    private var _binding : FragmentStorageBinding? = null
    private val binding get() = _binding!!

    private val storageAdapter : StorageAdapter by lazy {
        StorageAdapter(searchList = ArrayList())
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

}