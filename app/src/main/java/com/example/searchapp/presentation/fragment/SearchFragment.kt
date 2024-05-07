package com.example.searchapp.presentation.fragment

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

        // 데이터 업데이트
        searchViewModel.getSearchImageLiveData.observe(viewLifecycleOwner){
            Log.d("debugSearchData", it.toString())             // 전체 데이터

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

                sharedSave()

                return false    // false : 검색 키보드를 내림, return true : 검색 키보드를 내리지 않음
            }

            // 검색창에서 글자 변경이 일어날 때마다 호출
            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        }
        )
    }


    private fun sharedSave() {
        // fragment에서는 sharedPreferences 쓸려면 activtiy를 참조해서 써줘야함
        val sharedPreferences = activity?.getSharedPreferences("searchText", Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        val searchData = binding.searchView.setQuery(binding.searchView.query, false).toString()

        // 데이터 저장
        edit?.putString("searchData", searchData)
        edit?.apply()
    }


    fun sharedLoad(){
        val sharedPreferences = activity?.getSharedPreferences("searchText", Context.MODE_PRIVATE)
        // default값 설정해줘야함 -> 데이터가 null일떄(존재하지 않을떄의 값)
        // getString을 이용해 데이터를 꺼내서 사용
        val searchData = sharedPreferences?.getString("searchData", null)

        binding.searchView.setQuery(searchData, false)
    }
}


