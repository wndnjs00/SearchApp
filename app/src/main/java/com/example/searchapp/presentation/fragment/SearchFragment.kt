package com.example.searchapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.SearchRepositoryImpl
import com.example.searchapp.SearchViewModel
import com.example.searchapp.SearchViewModelFactory
import com.example.searchapp.databinding.FragmentSearchBinding
import com.example.searchapp.presentation.activity.SearchAdapter
import com.example.searchapp.retrofit.model.DocumentResponse
import com.example.searchapp.retrofit.model.SearchResponse
import com.example.searchapp.retrofit.network.RetrofitClient
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter : SearchAdapter by lazy {
        SearchAdapter(searchList = ArrayList<DocumentResponse>())
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
        setRecyclerView(documentList = ArrayList())

        searchViewModel.getSearchImageLiveData.observe(viewLifecycleOwner){
            Log.d("debugSearchData", it.toString())             // 전체 데이터

            Log.d("test_thumbnailUrl", it[0].thumbnailUrl)      // 이미지 url
            Log.d("test_displaySiteName", it[0].displaySiteName)  // 사이트이름
            Log.d("test_datetime", it[0].datetime)              // 날짜

            this.searchAdapter.submitList(ArrayList(it))
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    // 어뎁터와 리사이클러뷰 연결
    private fun setRecyclerView(documentList : ArrayList<DocumentResponse>){

        with(binding.recyclerViewSearch){
            adapter = searchAdapter   // 리사이클러뷰와 어뎁터 연결
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }


    }


    // searchView 동작이벤트함수
    private fun initSearchView() {

        binding.searchView.isSubmitButtonEnabled = true

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
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

