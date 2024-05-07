package com.example.searchapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.presentation.viewmodel.SearchViewModel
import com.example.searchapp.presentation.viewmodel.SearchViewModelFactory
import com.example.searchapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter : SearchAdapter by lazy {
        SearchAdapter(searchList = ArrayList())
    }

    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        initSearchView()
        setRecyclerView()

        searchViewModel.getSearchImageLiveData.observe(viewLifecycleOwner){
            Log.d("debugSearchData", it.toString())             // 전체 데이터
            Log.d("test_thumbnailUrl", it[0].thumbnailUrl)      // 이미지 url
            this.searchAdapter.submitList(ArrayList(it))
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    // 어뎁터와 리사이클러뷰 연결
    private fun setRecyclerView(){

        with(binding.recyclerViewSearch){
            adapter = searchAdapter   // 리사이클러뷰와 어뎁터 연결
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }


    // searchView 동작이벤트함수
    private fun initSearchView() {

        binding.searchView.isSubmitButtonEnabled = true

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            // 검색버튼 누를때 호출
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query!!.isNotEmpty()) {
                    searchViewModel.getSearchImageList(query)
                }

                return false    // false : 검색 키보드를 내림, return true : 검색 키보드를 내리지 않음
            }

            // 검색창에서 글자 변경이 일어날 때마다 호출
            override fun onQueryTextChange(newText: String?): Boolean {

//                if (newText != null) {
//                    searchViewModel.getSearchImageList(newText)
//                }

                return true
            }
        }

        )
    }


}

