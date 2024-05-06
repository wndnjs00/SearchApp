package com.example.searchapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.searchapp.databinding.FragmentSearchBinding
import com.google.android.material.search.SearchView


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        initSearchView()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // searchView 동작이벤트함수
    private fun initSearchView() {

        binding.searchView.isSubmitButtonEnabled = true

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            // 검색버튼 누를때 호출
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false    // false : 검색 키보드를 내림, return true : 검색 키보드를 내리지 않음
            }

            // 검색창에서 글자 변경이 일어날 때마다 호출
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        }

        )
    }

}

