package com.example.searchapp.presentation.fragment

import android.annotation.SuppressLint
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
        SearchAdapter(searchList = ArrayList()){
            // 클릭 시 이동
            adapterClick()
        }
    }

    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        searchBtn()
        removeText()

        // ViewModel을 observe해서 실시간 변경되는 데이터관찰
        searchViewModel.getSearchImageLiveData.observe(viewLifecycleOwner){
            Log.d("debugSearchData", it.toString())         // 전체 데이터

            // 데이터 업데이트
            this.searchAdapter.submitList(ArrayList(it))
            this.searchAdapter.notifyDataSetChanged()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    // 어뎁터와 리사이클러뷰 연결
    private fun setRecyclerView(){

        with(binding.recyclerViewSearch){
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }


    private fun searchBtn(){

        binding.searchBtn.setOnClickListener {
            saveSearchData()

            val searchText = binding.searchViewEt.text.toString()
            if (searchText.isNotEmpty()) {
                // viewModel에 작성한 텍스트값 전달
                searchViewModel.getSearchImageList(searchText)
            }
        }

        loadSearchData()
    }


    private fun removeText(){
        with(binding){
            searchClose.setOnClickListener {
                searchViewEt.setText("")
            }
        }
    }


    private fun saveSearchData(){
        // fragment에서는 sharedPreferences 쓸려면 activtiy를 참조해서 써줘야함
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        val searchData = binding.searchViewEt.text.toString()

        edit?.putString("searchData", searchData)
        edit?.apply()
    }

    private fun loadSearchData(){
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        // getString을 이용해 데이터를 꺼내서 사용
        // default값 설정해줘야함 -> 데이터가 null일떄(존재하지 않을떄의 값)
        val searchData = sharedPreferences?.getString("searchData", null)

        binding.searchViewEt.setText(searchData)
    }


    // 리사이클러뷰 아이템 클릭 이벤트
    private fun adapterClick(){

    }

}


