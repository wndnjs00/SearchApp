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

        setRecyclerView()
        searchBtn()

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


    private fun searchBtn(){
        binding.searchBtn.setOnClickListener {
            saveData()

            val searchText = binding.searchViewEt.text.toString()

            if (searchText.isNotEmpty()) {
                searchViewModel.getSearchImageList(searchText)
            }
        }
        loadData()
    }


    private fun saveData(){
        // fragment에서는 sharedPreferences 쓸려면 activtiy를 참조해서 써줘야함
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        val searchData = binding.searchViewEt.text.toString()

        // 데이터 저장
        edit?.putString("searchData", searchData)
        edit?.apply()
    }

    private fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        // getString을 이용해 데이터를 꺼내서 사용
        // default값 설정해줘야함 -> 데이터가 null일떄(존재하지 않을떄의 값)
        val searchData = sharedPreferences?.getString("searchData", null)

        binding.searchViewEt.setText(searchData)
    }


}


