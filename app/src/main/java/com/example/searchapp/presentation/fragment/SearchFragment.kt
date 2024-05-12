package com.example.searchapp.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.searchapp.data.SharedPreferences
import com.example.searchapp.data.model.DocumentResponse
import com.example.searchapp.presentation.viewmodel.SearchViewModel
import com.example.searchapp.presentation.viewmodel.SearchViewModelFactory
import com.example.searchapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchAdapter : SearchAdapter by lazy {
        SearchAdapter(searchList = ArrayList()){ search, position ->
            // 클릭 시
            adapterClick(search, position)
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        searchBtn()
        removeText()
        loadSearchData()    // 검색어 텍스트 불러와서 표시
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // 어뎁터와 리사이클러뷰 연결
    private fun setRecyclerView(){

        with(binding.recyclerViewSearch){
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun searchBtn(){

        binding.searchBtn.setOnClickListener {
            saveSearchData()    // 검색어 텍스트 저장
            hideKeyboard()      // 키보드 숨김

            val searchText = binding.searchViewEt.text.toString()
            if (searchText.isNotEmpty()) {
                // viewModel에 작성한 텍스트값 전달
                searchViewModel.getSearchImageList(searchText)
//                searchViewModel.getSearchVideoList(searchText)
            }else{
                Toast.makeText(requireContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        // ViewModel을 observe해서 실시간 변경되는 데이터(검색어)관찰
        searchViewModel.getSearchImageLiveData.observe(viewLifecycleOwner){
            Log.d("it_imageData", it.toString())         // 전체 데이터 (검색버튼 눌렀을때 가져오는 전체데이터)

            // 데이터 업데이트
            searchAdapter.clearItem()                   // 업데이트 하기전에 클리어 먼저해주자
            searchAdapter.submitImageList(it as ArrayList)   // 이 전체데이터를(it) submitList에 넣어주기
            searchAdapter.notifyDataSetChanged()
        }


        searchViewModel.getSearchVideoLiveData.observe(viewLifecycleOwner){

            Log.d("it_VideoData", it.toString())
            searchAdapter.clearItem()
            searchAdapter.submitVideoList(it as ArrayList)
            searchAdapter.notifyDataSetChanged()
        }

    }


    private fun removeText(){
        with(binding){
            searchClose.setOnClickListener {
                searchViewEt.setText("")
            }
        }
    }


    // 검색어 텍스트 저장
    private fun saveSearchData(){
        // fragment에서는 sharedPreferences 쓸려면 activtiy를 참조해서 써줘야함
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        val searchData = binding.searchViewEt.text.toString()

        edit?.putString("searchData", searchData)
        edit?.apply()
    }

    // 검색어 텍스트 불러와서 표시
    private fun loadSearchData(){
        val sharedPreferences = activity?.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
        // getString을 이용해 데이터를 꺼내서 사용
        // default값 설정해줘야함 -> 데이터가 null일때(존재하지 않을떄의 값)
        val searchData = sharedPreferences?.getString("searchData", null)

        binding.searchViewEt.setText(searchData)
    }


    // 키보드 숨기는 함수
    @SuppressLint("ServiceCast")
    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    // 리사이클러뷰 아이템 클릭 이벤트
    private fun adapterClick(documentResponse: DocumentResponse, position : Int){

        val item = searchAdapter.searchList[position]

        // isLike 뒤집기 (isLike가 true면 false로, false면 ture로)
        item.isLike = !item.isLike

        if (item.isLike){
            // ture이면 SharedPreferences를 통해 클릭한 아이템 추가
            SharedPreferences.addPrefItem(context, item)
            item.isLike = true

        }else{
            // 한번 더 클릭된다면, 클릭한 아이템 삭제
            SharedPreferences.deletePrefItem(context, item)
            item.isLike = false
        }

        Log.d("item.isLike", item.isLike.toString())

        // 아이템 업데이트
        searchAdapter.notifyItemChanged(position)
    }


}


